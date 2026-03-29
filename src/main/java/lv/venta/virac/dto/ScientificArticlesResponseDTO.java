package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScientificArticlesResponseDTO {
	
	private int idArticle;
	private String name;
	private String coAuthors;
	private int idJournal;
	private String articleComments;
	private String publicationLink;

}
