package com.stackroute.repository;

import com.stackroute.domain.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AuthenticationRepository extends JpaRepository<Authentication,String> {
}
