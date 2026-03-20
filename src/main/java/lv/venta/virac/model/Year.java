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
@Table(name = "yearTable")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE year_table SET deleted = true WHERE id_year=?")
@FilterDef(name = "deletedYearFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedYearFilter", condition = "deleted = :isDeleted")
public class Year {
	
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
	
	@Column(nullable = true ,updatable = false)
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
	
	public Year(Integer yearNumber) {
		setYearNumber(yearNumber);
	}
}
