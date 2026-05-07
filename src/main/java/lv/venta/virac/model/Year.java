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
import lv.venta.virac.scheduler.PlanSchedule;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "yearTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE year_table SET deleted = true WHERE id_year=?")
@FilterDef(name = "deletedYearFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedYearFilter", condition = "deleted = :isDeleted")
public class Year extends Auditable{
	
	@Id
	@Column(name = "idYear")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idYear;
	
	@Column(name = "yearNumber")
	@NotNull
	//@Pattern(regexp = "^[0-9]{4}$", message = "Ievdiet pareizu gada skaitli")
	private int yearNumber;
	
	@OneToMany(mappedBy = "year")
	@ToString.Exclude
	private Collection<Plan> plan;
	
	@OneToMany(mappedBy = "year")
	@ToString.Exclude
	private Collection<PlanSchedule> planSchedule;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public Year(Integer yearNumber) {
		setYearNumber(yearNumber);
	}
}
