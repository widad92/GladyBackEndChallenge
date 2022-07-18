package com.weDooChallenge.weDooChallengeBackEnd.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;
import com.weDooChallenge.weDooChallengeBackEnd.models.Card;
import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;
import com.weDooChallenge.weDooChallengeBackEnd.models.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	public  List< Voucher> findByDepositTypeAndEmployee(DepositType depositType,Employee employee);

}
