package com.gbsfo.test.task.repository;

import com.gbsfo.test.task.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM _order o WHERE o.user.id = (:id)")
    List<Order> findAllByCreatorId(Long id);
}
