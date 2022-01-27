package org.iesfm.shop.api.controller;

import org.iesfm.shop.Item;
import org.iesfm.shop.Order;
import org.iesfm.shop.exceptions.ArticleNotFoundException;
import org.iesfm.shop.exceptions.ClientNotFoundException;
import org.iesfm.shop.exceptions.EmptyOrderException;
import org.iesfm.shop.repository.ArticleRepository;
import org.iesfm.shop.repository.ClientRepository;
import org.iesfm.shop.repository.OrderRepository;
import org.iesfm.shop.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/orders")
    public void insert(@RequestBody Order order) {
        try {
            if (!orderService.insert(order)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "");
            }
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encuentra el artículo " + e.getId());
        } catch (EmptyOrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido vacío");
        } catch (ClientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el cliente " + e.getNif());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{nif}/orders")
    public List<Order> listByNif(@PathVariable("nif") String nif) {
        try {
            return orderService.listByNif(nif);
        } catch (ClientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el cliente " + e.getNif());
        }
    }
}
