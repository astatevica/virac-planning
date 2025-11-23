package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
	@Id
	@Column(name = "idArticlePlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idArticlePlan;
	
	//TODO: connect
	private int idPlan;
	
	//TODO: connect
	private int idArticle;
	
	@Column(name = "articleComments")
	private String articleComments;
	
	@Column(name = "publicationLink")
	private String publicationLink;
}
