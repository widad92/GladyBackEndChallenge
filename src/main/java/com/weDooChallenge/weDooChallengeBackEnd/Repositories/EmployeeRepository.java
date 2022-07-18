package com.weDooChallenge.weDooChallengeBackEnd.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weDooChallenge.weDooChallengeBackEnd.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	

}
