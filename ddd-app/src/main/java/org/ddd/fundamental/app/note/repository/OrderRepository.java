package org.ddd.fundamental.app.note.repository;

import org.ddd.fundamental.app.note.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
}
