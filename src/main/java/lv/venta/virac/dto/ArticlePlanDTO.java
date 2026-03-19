package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticlePlanDTO {

	private int idArticlePlan;
	private int idPlan;
	private int idScientificArticles;
	private String articleComments;
	private String publicationLink;
	
}