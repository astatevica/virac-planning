package lv.venta.virac.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScientificArticlesCommentsDTO {

	//Can be empty if coming from new course entity
	private int idArticle;
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
	private String name;
	
	@Size(min = 3, max = 50, message = "coAuthors field must be between 3 and 50 characters")
	private String coAuthors;
	
	@Min(value = 1, message = "idJournal must be not null")
	private int idJournal;
	
	@Size(min = 0, max = 150, message = "Comments must be between 0 and 150 characters")
	private String articleComments;
	
	@Size(min = 3, max = 50, message = "Link must be between 3 and 50 characters")
	private String publicationLink;
}
