package org.iesfm.shop.repository;

import org.iesfm.shop.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, Integer> {
    Article findById(int id);
}
