package org.iesfm.shop.repository;

import org.iesfm.shop.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findById(int orderId);
    List<Order> findByClientNif(String nif);
}
