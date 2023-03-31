package com.gbsfo.test.task.repository;

import com.gbsfo.test.task.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.id IN (:ids)")
    Set<Item> findByIds(@Param("ids") List<Long> ids);

    @Query("SELECT i FROM Item i WHERE i.user.id = (:id)")
    List<Item> findAllByCreatorId(Long id);
}
