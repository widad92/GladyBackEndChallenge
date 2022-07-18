package com.weDooChallenge.weDooChallengeBackEnd.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "vouchers")
public class Voucher implements Serializable {
	
	//@EmbeddedId
	//private CompanyDepositKey  id;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ToString.Exclude
	@ManyToOne
    //@MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    Employee employee;

	@ToString.Exclude
    @ManyToOne
    //@MapsId("companyId")
    @JoinColumn(name = "company_id")
    Company company;

	private double amount;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private DepositType depositType;
	
	public Voucher(Long id, Employee employee, Company company, double amount, LocalDate dateDebut
			, DepositType depositType) {
		
		//this.id = id;
		this.employee = employee;
		this.company = company;
		this.amount = amount;
		this.dateDebut = dateDebut;
		this.depositType = depositType;
		
		if(depositType.equals(depositType.GIFT)){
			this.dateFin =	dateDebut.plusYears(1);
		}else {
			int nextYear=dateDebut.getYear()+1;
			this.dateFin =LocalDate.of(nextYear, 2, 28);
		}
	
	}

	
	
}
