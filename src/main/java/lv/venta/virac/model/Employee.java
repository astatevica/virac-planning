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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.virac.user.User;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "employeeTable")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE employee_table SET deleted = true WHERE id_employee=?")
@FilterDef(name = "deletedEmployeeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedEmployeeFilter", condition = "deleted = :isDeleted")
public class Employee {
	
	@Id
	@Column(name = "idEmployee")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idEmployee;
	
	@Column(name = "name")
	//@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String name;
	
	@Column(name = "surname")
	//@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String surname;
	
	@ManyToOne
	@JoinColumn(name = "idDepartment")
	private ViracDepartment viracDepartment;
	
	@Column(name = "adress")
	@NotNull
	private String position;
	
	@OneToMany(mappedBy = "employee")
	@ToString.Exclude
	private Collection<ProjectManagement>  projectManagement;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
	@ToString.Exclude
	private Collection<Plan> plan;
	
	@Column(nullable = false,updatable = false)
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
	
	@OneToOne(mappedBy = "employee")
	@ToString.Exclude
	private User user;
	
	public Employee(String name, String surname,ViracDepartment viracDepartment, String position) {
		setName(name);
		setSurname(surname);
		setViracDepartment(viracDepartment);
		setPosition(position);
	}
}
