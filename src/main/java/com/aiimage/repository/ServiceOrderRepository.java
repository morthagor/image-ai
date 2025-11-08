package com.aiimage.repository;

import com.aiimage.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    Optional<ServiceOrder> findByProtocolNumber(String protocolNumber);
}
