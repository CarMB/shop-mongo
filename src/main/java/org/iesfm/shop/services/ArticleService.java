package org.iesfm.shop.services;

import org.iesfm.shop.Article;
import org.iesfm.shop.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public boolean insert(Article article) {
        if (articleRepository.existsById(article.getId())) {
            return false;
        }
        articleRepository.insert(article);
        return true;
    }
}
