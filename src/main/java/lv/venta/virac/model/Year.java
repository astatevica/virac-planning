package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "yearTable")
@ToString
@Entity
public class Year {
	
	@Id
	@Column(name = "idYear")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idYear;
	
	@Column(name = "yearNumber")
	@NotNull
	@Pattern(regexp = "^[0-9]{4}$", message = "Ievdiet pareizu gada skaitli")
	private int yearNumber;
	
	@OneToMany(mappedBy = "year")
	@ToString.Exclude
	private Plan plan;
	
	public Year(Integer yearNumber) {
		setYearNumber(yearNumber);
	}
}
