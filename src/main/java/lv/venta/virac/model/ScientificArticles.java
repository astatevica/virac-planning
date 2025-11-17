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
@Table(name = "scientifivArticlesTable")
@ToString
@Entity
public class ScientificArticles {
	private int idArticle;
	
	private String name;
	
	private String coAuthors;
	
	//TODO: connect
	private int idJournal;
}
