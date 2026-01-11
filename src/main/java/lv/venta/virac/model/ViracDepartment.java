package lv.venta.virac.model;

import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "viracDepartmentTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE viracDepartmentTable SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@FilterDef(name = "deletedDepartmentFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedDepartmentFilter", condition = "deleted = :isDeleted")
public class ViracDepartment {

	@Id
	@Column(name = "idDepartment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idDepartment;
	
	@Column(name = "name",unique = true)
	@NotNull
	//@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	//@Size(max = 20, min = 2)
	private String name;
	
	@OneToMany(mappedBy = "viracDepartment")
	@ToString.Exclude
	@JsonIgnore
	private Collection<Employee> employee;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public ViracDepartment(String name){
		setName(name);
	}
}
