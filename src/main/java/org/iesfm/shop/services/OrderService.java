package org.iesfm.shop.services;

import org.iesfm.shop.Article;
import org.iesfm.shop.Item;
import org.iesfm.shop.Order;
import org.iesfm.shop.exceptions.ArticleNotFoundException;
import org.iesfm.shop.exceptions.ClientNotFoundException;
import org.iesfm.shop.exceptions.EmptyOrderException;
import org.iesfm.shop.exceptions.OrderNotFoundException;
import org.iesfm.shop.repository.ArticleRepository;
import org.iesfm.shop.repository.ClientRepository;
import org.iesfm.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private ArticleRepository articleRepository;

    public OrderService(
            ClientRepository clientRepository,
            OrderRepository orderRepository,
            ArticleRepository articleRepository
    ) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.articleRepository = articleRepository;
    }

    public boolean insert(Order order) throws ArticleNotFoundException, EmptyOrderException, ClientNotFoundException {
        // Comprueba si ya existe el pedido
        if (orderRepository.existsById(order.getId())) {
            return false;
        }
        // Comprueba si existe el cliente
        if (!clientRepository.existsById(order.getClientNif())) {
            throw new ClientNotFoundException(order.getClientNif());
        }
        // Comprueba que el pedido no está vacío
        if (order.getItems().isEmpty()) {
            throw new EmptyOrderException();
        }

        for (Item item : order.getItems()) {
            if (!articleRepository.existsById(item.getArticleId())) {
                throw new ArticleNotFoundException(item.getArticleId());
            }
        }

        orderRepository.insert(order);
        return true;
    }

    public List<Order> listByNif(String nif) throws ClientNotFoundException {
        if (!clientRepository.existsById(nif)) {
            throw new ClientNotFoundException(nif);
        }
        return orderRepository.findByClientNif(nif);
    }

    public List<Article> listOrderArticles(int orderId) throws OrderNotFoundException {
//        Optional<Order> optOrder = orderRepository
//                .findById(orderId);
//        return optOrder
//                .map(
//                        order ->
//                                // TODO SACAR LOS ARTICULOS
//                                new ArrayList()
//                ).orElseThrow(() -> new OrderNotFoundException(orderId));
        Order order = orderRepository.findById(orderId);
        if(order == null) {
            throw new OrderNotFoundException(orderId);
        }
        List<Article> articles = new LinkedList<>();
        for(Item item: order.getItems()) {
            Article article = articleRepository.findById(item.getArticleId());
            articles.add(article);
        }
        return articles;
    }
}
