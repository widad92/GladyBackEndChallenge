package com.weDooChallenge.weDooChallengeBackEnd.Services;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.CardRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.CompanyRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.EmployeeRepository;
import com.weDooChallenge.weDooChallengeBackEnd.Repositories.VoucherRepository;
import com.weDooChallenge.weDooChallengeBackEnd.models.Card;
import com.weDooChallenge.weDooChallengeBackEnd.models.Company;
import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;
import com.weDooChallenge.weDooChallengeBackEnd.models.Voucher;

class testVoucherService {
	
	@Mock
	VoucherRepository voucherRepo;
	@Mock
	CompanyRepository companyRepo;
	@Mock
	EmployeeRepository employeeRepo;
	@Mock 
	CardRepository cardRepo;
	 
	@InjectMocks
	VoucherServiceImpl voucherService;

	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOfferAvoucher_GIFT() throws Exception {
       
		LocalDate endDateGift=LocalDate.now().plusYears(1);
	 
		
		
		//Create companyGift
		Optional<Company> companyGift=Optional.ofNullable(new Company(1L, 1142346, "companyGift",1000, null));
		
		
		//Create Employee
		Optional<Employee> employee1=Optional.ofNullable(new Employee(1L,"employee1",null, null));
		
		//create employee's wallet
		Card cardGift =new Card(1L,DepositType.GIFT,0,employee1.get());
		Card cardFood =new Card(2L,DepositType.FOOD,0,employee1.get());
				
		List<Card> wallets=new ArrayList<Card>();
		wallets.add(cardGift);
		wallets.add(cardFood);
		
		
		//set employee wallet
		employee1.get().setCards(wallets);
		
		//Create Vouchers
		Voucher voucher1=new Voucher(1L, employee1.get(), companyGift.get(), 100.0,LocalDate.now(), DepositType.GIFT);
		
		
		when(companyRepo.findById(anyLong())).thenReturn(companyGift);
		when(employeeRepo.findById(anyLong())).thenReturn(employee1);
		when(cardRepo.findByDepositTypeAndEmployee(any(DepositType.class), any(Employee.class))).thenReturn(cardGift);
        when(cardRepo.save(any(Card.class))).thenReturn(cardGift);
		when(cardRepo.save(any(Card.class))).thenReturn(cardFood);
		when(voucherRepo.save(any(Voucher.class))).thenReturn(voucher1);
		
		//Call the service
		Voucher giftVoucher=voucherService.offerAvoucher(companyGift.get(),employee1.get(),DepositType.GIFT, 100, LocalDate.now());

		//Asserts
		assertNotNull(giftVoucher);
		assertTrue(endDateGift.equals(giftVoucher.getDateFin()));
	    assertEquals(100, employee1.get().getCards().get(0).getAmount());
	}
	
	@Test
	void testOfferAvoucher_FOOD() throws Exception {
       
		int nextYear=LocalDate.now().getYear()+1;
		LocalDate endDateFood= LocalDate.of(nextYear, 2, 28);
		
		//Create companyGift
		
		Optional<Company> companyFood=Optional.ofNullable(new Company(2L, 1142346, "companyFood",500, null));
		
		//Create Employee
		Optional<Employee> employee1=Optional.ofNullable(new Employee(1L,"employee1",null, null));
		
		//create employee's wallet
		Card cardGift =new Card(1L,DepositType.GIFT,0,employee1.get());
		Card cardFood =new Card(2L,DepositType.FOOD,200,employee1.get());
				
		List<Card> wallets=new ArrayList<Card>();
		wallets.add(cardGift);
		wallets.add(cardFood);
		
		
		//set employee wallet
		employee1.get().setCards(wallets);
		
		//Create Vouchers
		Voucher voucher2=new Voucher(2L, employee1.get(), companyFood.get(), 50.0,LocalDate.now(), DepositType.FOOD);
		
		when(companyRepo.findById(anyLong())).thenReturn(companyFood);
		when(employeeRepo.findById(anyLong())).thenReturn(employee1);
	    when(cardRepo.findByDepositTypeAndEmployee(any(DepositType.class), any(Employee.class))).thenReturn(cardFood);
	    when(cardRepo.save(any(Card.class))).thenReturn(cardGift);
	    when(cardRepo.save(any(Card.class))).thenReturn(cardFood);
	    when(voucherRepo.save(any(Voucher.class))).thenReturn(voucher2);
		
		
		//Call the service
		Voucher foodVoucher=voucherService.offerAvoucher(companyFood.get(),employee1.get(),DepositType.FOOD, 50, LocalDate.now());
		
		//Asserts
		assertNotNull(foodVoucher);
		assertTrue(endDateFood.equals(foodVoucher.getDateFin()));
		assertEquals(250, employee1.get().getCards().get(1).getAmount());
		verify(voucherRepo,times(1)).save(foodVoucher);
	}

	@Test
	void testGetEmployeeBalance() {
		
		
		//Create companyGift
		Optional<Company> companyGift=Optional.ofNullable(new Company(1L, 1142346, "companyGift",1000, null));
		
		//Create Employee
		Optional<Employee> employee1=Optional.ofNullable(new Employee(1L,"employee1",null, null));
		
		//create employee's wallet
		Card cardGift =new Card(1L,DepositType.GIFT,0,employee1.get());
		Card cardFood =new Card(2L,DepositType.FOOD,0,employee1.get());
				
		List<Card> wallets=new ArrayList<Card>();
		wallets.add(cardGift);
		wallets.add(cardFood);
		
		//set employee wallet
		employee1.get().setCards(wallets);
		
		List<Voucher> vouchers=new ArrayList<Voucher>();
		Voucher voucher1=new Voucher(1L, employee1.get(), companyGift.get(), 100.0,LocalDate.of(2019, 01, 01) ,DepositType.GIFT);
		Voucher voucher2=new Voucher(2L, employee1.get(), companyGift.get(), 600.0,LocalDate.now(), DepositType.GIFT);
		vouchers.add(voucher1);
		vouchers.add(voucher2);
		
		when(voucherRepo.findByDepositTypeAndEmployee(any(DepositType.class),any(Employee.class))).thenReturn(vouchers);
		Double balance=voucherService.getEmployeeBalance(employee1.get(), DepositType.GIFT);
		
		assertNotNull(balance);
	    assertEquals(600, balance);
		
		
		
	}

}
