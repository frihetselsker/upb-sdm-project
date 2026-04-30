package com.example.publictransport.repository;

import com.example.publictransport.domain.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ValidatorRepository extends JpaRepository<Validator, Long> {
    Optional<Validator> findByValidatorId(String validatorId);
}
