package lv.venta.virac.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticlePlanCommentsDTO {

	@Size(min = 0, max = 150, message = "Comments must be between 0 and 150 characters")
	private String comments;
	
	@Size(min = 3, max = 50, message = "Link must be between 3 and 50 characters")
	private String link;
}
