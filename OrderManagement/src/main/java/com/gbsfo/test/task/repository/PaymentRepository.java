package com.gbsfo.test.task.repository;

import com.gbsfo.test.task.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.user.id = (:id)")
    List<Payment> findAllByCreatorId(Long id);
}
