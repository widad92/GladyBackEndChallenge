package com.weDooChallenge.weDooChallengeBackEnd.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weDooChallenge.weDooChallengeBackEnd.models.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {

}
