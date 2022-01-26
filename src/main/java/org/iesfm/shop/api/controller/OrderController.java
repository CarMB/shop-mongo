package org.iesfm.shop.api.controller;

import org.iesfm.shop.Item;
import org.iesfm.shop.Order;
import org.iesfm.shop.repository.ArticleRepository;
import org.iesfm.shop.repository.ClientRepository;
import org.iesfm.shop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private ArticleRepository articleRepository;

    public OrderController(ClientRepository clientRepository, OrderRepository orderRepository, ArticleRepository articleRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.articleRepository = articleRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/orders")
    public void insert(@RequestBody Order order) {
        // Comprueba si ya existe el pedido
        if (orderRepository.existsById(order.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "");
        }
        // Comprueba si existe el cliente
        if (!clientRepository.existsById(order.getClientNif())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el cliente");
        }
        // Comprueba que el pedido no está vacío
        if (order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Debe haber algún item");
        }

        for (Item item : order.getItems()) {
            if (!articleRepository.existsById(item.getArticleId())) {
                throw new ResponseStatusException(
                        HttpStatus.MULTI_STATUS,
                        "No existe el artículo " + item.getArticleId()
                );
            }
        }

        orderRepository.insert(order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{nif}/orders")
    public List<Order> listByNif(@PathVariable("nif") String nif) {
        if (!clientRepository.existsById(nif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
        return orderRepository.findByClientNif(nif);
    }
}
