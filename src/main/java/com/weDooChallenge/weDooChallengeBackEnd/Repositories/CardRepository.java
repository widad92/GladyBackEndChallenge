package com.weDooChallenge.weDooChallengeBackEnd.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weDooChallenge.weDooChallengeBackEnd.Enums.DepositType;
import com.weDooChallenge.weDooChallengeBackEnd.models.Card;
import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;


public interface CardRepository extends JpaRepository<Card, Long> {
	
	public  Card findByDepositTypeAndEmployee(DepositType depositType,Employee employee);

}
