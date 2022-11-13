package com.zk.domain.repository;


import com.zk.domain.core.root.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
