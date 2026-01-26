package lv.venta.virac.user;

import java.util.List;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ProjectManagement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Table(name = "user_table")
//@SQLDelete(sql = "UPDATE user_table SET deleted = true WHERE id_user=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
//@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
public class User implements UserDetails{

	@Id
	@Column(name = "idUser")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public User(String firstname, String lastname,String email, String password, Role role) {
		setFirstname(firstname);
		setLastname(lastname);
		setEmail(email);
		setPassword(password);
		setRole(role);
	}


}
