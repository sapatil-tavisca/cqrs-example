package com.affinion.customereventsource.repository;

import com.affinion.customereventsource.domain.CustomerEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerEventRepository extends JpaRepository<CustomerEvent, Long> {

    Optional<CustomerEvent> findByCustomerId(Long id);
}
