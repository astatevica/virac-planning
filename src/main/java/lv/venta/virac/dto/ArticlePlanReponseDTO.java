package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ArticlePlanReponseDTO {

	private int idPlan;
	private int idArticle;
	private String name;
	private String coAuthors;
	private int idJournal; //TODO: te varbūt jāpārtaisa
	private String articleComments;
	private String publicationLink;
}
