package lv.venta.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lv.venta.virac.user.User;

public class ApplicationAuditAware implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor(){
				
		//retrieves the Authentication object. Gets details about current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
			return Optional.empty();
		}

		//checking if the user is authenticated
	    if (authentication == null //no authentication information is available, so the user is considered unauthenticated
	    		|| !authentication.isAuthenticated() //user isn't fully authenticated
	            || authentication instanceof AnonymousAuthenticationToken) { //user is anonymous
	        return Optional.empty();
	    }

	    //retrieves the principal object from the Authentication instance
	    Object principal = authentication.getPrincipal();
 
	    //retrieves all information from logged in user
	    if (principal instanceof User currentUser) {
			System.out.println(currentUser);
	        return Optional.ofNullable(currentUser.getIdUser());
	    }

	    //returns null if not authenticated
	    return Optional.empty();
	    
	
	}
}
