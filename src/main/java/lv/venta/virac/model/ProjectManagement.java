package lv.venta.virac.model;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "projectManagementTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE projectManagementTable SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ProjectManagement {
	
	@Id
	@Column(name = "idProjectManag")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idProjectManag;
	
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;
	
	@Column(name = "startDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	
	@Column(name = "endDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;
	
	@OneToOne(mappedBy = "projectManagement")
	@ToString.Exclude
	private Project project;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public ProjectManagement(Employee employee, LocalDate startDate, LocalDate endDate) {
		setEmployee(employee);
		setStartDate(startDate);
		setEndDate(endDate);
	}
}
