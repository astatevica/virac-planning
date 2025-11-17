package lv.venta.virac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "articlePlanTable")
@ToString
@Entity
public class ArticlePlan {
	private int idArticlePlan;
	
	//TODO: connect
	private int idPlan;
	
	//TODO: connect
	private int idArticle;
	
	private String articleComments;
	
	private String publicationLink;
}
