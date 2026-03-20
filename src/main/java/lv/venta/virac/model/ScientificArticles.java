package lv.venta.virac.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "scientificArticlesTable")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE scientific_articles_table SET deleted = true WHERE id_article=?")
@FilterDef(name = "deletedScientificArticlesFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedScientificArticlesFilter", condition = "deleted = :isDeleted")
public class ScientificArticles {
	
	@Id
	@Column(name = "idArticle")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idArticle;
	
	@Column(name = "name")
	@NotNull
	//@Size(max = 50, min = 2)
	private String name;
	
	@Column(name = "coAuthors")
	@NotNull
	//@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ,]+", message = "Tikai burti, komats un atstarpes ir atlautas")
	//@Size(max = 20, min = 2)
	private String coAuthors;
	
	@ManyToOne
	@JoinColumn(name = "idJournal")
	private Journal journal;
	
	@OneToMany(mappedBy = "scientificArticles")
	@ToString.Exclude
	private Collection<ArticlePlan> articlePlan;
	
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
	
	public ScientificArticles(String name, String coAuthors, Journal journal) {
		setName(name);
		setCoAuthors(coAuthors);
		setJournal(journal);
	}
}
