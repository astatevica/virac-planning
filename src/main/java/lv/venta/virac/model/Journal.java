package lv.venta.virac.model;

import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "journalTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE journal_table SET deleted = true WHERE id_journal=?")
@FilterDef(name = "deletedJournalFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedJournalFilter", condition = "deleted = :isDeleted")
public class Journal {
	
	@Id
	@Column(name = "idJournal")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idJournal;
	
	@Column(name = "name")
	@NotNull
	private String name;
	
	@OneToMany(mappedBy = "journal")
	@ToString.Exclude
	private Collection<ScientificArticles>  scientificArticles;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public Journal(String name) {
		setName(name);
	}
}
