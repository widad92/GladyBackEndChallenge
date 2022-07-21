package com.weDooChallenge.weDooChallengeBackEnd.Services;

import java.time.LocalDate;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;
import com.weDooChallenge.weDooChallengeBackEnd.models.Company;
import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;
import com.weDooChallenge.weDooChallengeBackEnd.models.Voucher;

public interface VoucherService {
	
	public Voucher offerAvoucher(Company company ,Employee employee,DepositType depoType,double amount,LocalDate startDate) throws Exception;

	public double getEmployeeBalance(Employee employee,DepositType depoType);
}
