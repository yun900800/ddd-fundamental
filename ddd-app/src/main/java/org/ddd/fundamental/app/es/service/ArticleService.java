package org.ddd.fundamental.app.es.service;

import org.ddd.fundamental.app.es.repository.ArticleRepository;
import org.ddd.fundamental.app.es.repository.model.Article;
import org.ddd.fundamental.app.es.repository.model.Author;
import org.ddd.fundamental.share.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import static java.util.Arrays.asList;

@Service
public class ArticleService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleRepository articleRepository;

    private final Author johnSmith = new Author("John Smith");
    private final Author johnDoe = new Author("John Doe");

    public void init() {
        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(asList(johnSmith, johnDoe));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Search engines");
        article.setAuthors(asList(johnDoe));
        article.setTags("search engines", "tutorial");
        articleRepository.save(article);

        article = new Article("Second Article About Elasticsearch");
        article.setAuthors(asList(johnSmith));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Elasticsearch Tutorial");
        article.setAuthors(asList(johnDoe));
        article.setTags("elasticsearch");
        articleRepository.save(article);
    }

    public void destroy(){
        articleRepository.deleteAll();
    }
}
