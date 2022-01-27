package org.iesfm.shop.api.controller;

import org.iesfm.shop.Article;
import org.iesfm.shop.exceptions.OrderNotFoundException;
import org.iesfm.shop.services.ArticleService;
import org.iesfm.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleService articleService;
    private OrderService orderService;

    public ArticleController(ArticleService articleService, OrderService orderService) {
        this.articleService = articleService;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/articles")
    public void insert(@RequestBody Article article) {
        if (!articleService.insert(article)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/{orderId}/articles")
    public List<Article> getOrderArticles(@PathVariable("orderId") int orderId) {
        try {
            return orderService.listOrderArticles(orderId);
        } catch (OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pedido " + e.getOrderId());
        }
    }
}
