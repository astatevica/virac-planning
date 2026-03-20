package lv.venta.virac.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE article_plan_table SET deleted = true WHERE id_article_plan=?")
@FilterDef(name = "deletedArticlePlanFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedArticlePlanFilter", condition = "deleted = :isDeleted")
public class ArticlePlan{
	@Id
	@Column(name = "idArticlePlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idArticlePlan;
	
	@ManyToOne
	@JoinColumn(name = "idPlan")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "idArticle")
	private ScientificArticles scientificArticles;
	
	@Column(name = "articleComments")
	private String articleComments;
	
	@Column(name = "publicationLink")
	private String publicationLink;
	
	@CreatedDate
	@Column(nullable = true,updatable = false)
	@JsonIgnore
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	@JsonIgnore
	private LocalDateTime lastModified;
	
	@CreatedBy
	@Column(updatable = false)
	@JsonIgnore
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	@JsonIgnore
	private Integer lastModifiedBy;

	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public ArticlePlan(Plan plan, ScientificArticles scientificArticles, String articleComments, String publicationLink) {
		setPlan(plan);
		setScientificArticles(scientificArticles);
		setArticleComments(articleComments);
		setPublicationLink(publicationLink);
	}
}
