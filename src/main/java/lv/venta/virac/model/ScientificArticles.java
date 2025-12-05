package lv.venta.virac.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class ScientificArticles {
	
	@Id
	@Column(name = "idArticle")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idArticle;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 50, min = 2)
	private String name;
	
	@Column(name = "coAuthors")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ,]+", message = "Tikai burti, komats un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String coAuthors;
	
	@ManyToOne
	@JoinColumn(name = "idJournal")
	private Journal journal;
	
	@OneToMany(mappedBy = "scientificArticles")
	@ToString.Exclude
	private Collection<ArticlePlan> articlePlan;
	
	public ScientificArticles(String name, String coAuthors, Journal journal) {
		setName(name);
		setCoAuthors(coAuthors);
		setJournal(journal);
	}
}
