package lv.venta.virac.scheduler;

import java.time.LocalDate;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lv.venta.virac.model.Year;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "planScheduleTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE plan_schedule_table SET deleted = true WHERE id_plan_schedule=?")
@FilterDef(name = "deletedPlanScheduleFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedPlanScheduleFilter", condition = "deleted = :isDeleted")
public class PlanSchedule {
	
	@Id
	@Column(name = "idPlanSchedule")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idPlanSchedule;
	
	@ManyToOne
	@JoinColumn(name = "idYear")
	private Year year;
	
	@Column(name = "plannedFreezeDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate plannedFreezeDate;
	
	@Column(name = "doneFreezeDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate doneFreezeDate;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public PlanSchedule(Year year, LocalDate plannedFreezeDate, LocalDate doneFreezeDate) {
		setYear(year);
		setPlannedFreezeDate(plannedFreezeDate);
		setDoneFreezeDate(doneFreezeDate);
	}

}
