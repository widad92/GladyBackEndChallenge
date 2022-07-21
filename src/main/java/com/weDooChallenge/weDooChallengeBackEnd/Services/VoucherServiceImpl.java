package com.weDooChallenge.weDooChallengeBackEnd.Services;

import java.time.LocalDate;
import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;
import com.weDooChallenge.weDooChallengeBackEnd.Exceptions.CompanyBalanceException;
import com.weDooChallenge.weDooChallengeBackEnd.Exceptions.CompanyOrEmployeeNotFoundlException;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.CardRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.CompanyRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.EmployeeRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.VoucherRepository;
import com.weDooChallenge.weDooChallengeBackEnd.models.Card;
import com.weDooChallenge.weDooChallengeBackEnd.models.Company;
 
import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;
import com.weDooChallenge.weDooChallengeBackEnd.models.Voucher;

   @Service
   public class VoucherServiceImpl implements VoucherService {
	
   @Autowired
   VoucherRepository voucherRepo;
   @Autowired
   CompanyRepository companyRepo;
   @Autowired
   EmployeeRepository employeeRepo;
   @Autowired
   CardRepository cardRepo;
   Double balance=0.0;


    @Override
	@Transactional
	public Voucher offerAvoucher(Company company ,Employee employee,DepositType depoType,double amount,LocalDate startDate) throws CompanyOrEmployeeNotFoundlException, CompanyBalanceException
	{
		Voucher voucher ;
		double cardAmount;
		Company companyExist=companyRepo.findById(company.getId()).orElse(null);
		Employee employeeExist=employeeRepo.findById(employee.getId()).orElse(null);
		
		
		if(companyExist ==null || employeeExist==null )
		{
			throw new CompanyOrEmployeeNotFoundlException("company or employee not found!");
		}
		
		double currentBlance=company.getBalance();
		
		if(currentBlance >= amount) {
	
		 voucher =voucherRepo.save(new Voucher(null, employeeExist, companyExist, amount, startDate, depoType));
		
		 company.setBalance(currentBlance-amount);
		
		if(company.getVouchers()!=null) {
			
		company.getVouchers().add(voucher);
	
		}
	
		companyRepo.save(company);
		
		if(employee.getVouchers()!=null) {
		employee.getVouchers().add(voucher);
		}
		
		Card card =cardRepo.findByDepositTypeAndEmployee(depoType, employee);
		cardAmount=card.getAmount();
		card.setAmount(cardAmount+amount);
		cardRepo.save(card);
		
		return voucher;
		
		}else {
			
			throw new CompanyBalanceException("balance does not allow it");
		}
		
	}
	
    @Override
	public double getEmployeeBalance(Employee employee,DepositType depoType) {
		
		List<Voucher> vouchers = voucherRepo.findByDepositTypeAndEmployee(depoType, employee);
		LocalDate todayDate =LocalDate.now(); 
		
		if(vouchers !=null) {
			
		 vouchers.stream().forEach(v->{
			 if(v.getDateFin().isAfter(todayDate)|| v.getDateFin().isEqual(todayDate))
			 {
				balance+=v.getAmount(); 
			 }
			 
			 
		 });
		}
		 
		 return balance;
	
	}
	
	

}
