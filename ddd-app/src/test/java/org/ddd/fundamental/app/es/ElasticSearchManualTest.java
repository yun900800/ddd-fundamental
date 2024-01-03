package org.ddd.fundamental.app.es;

import org.apache.commons.beanutils.BeanUtils;
import org.ddd.fundamental.app.config.EsConfig;
import org.ddd.fundamental.app.es.repository.ArticleRepository;
import org.ddd.fundamental.app.es.repository.model.Article;
import org.ddd.fundamental.app.es.repository.model.Author;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.elasticsearch.index.query.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EsConfig.class)
public class ElasticSearchManualTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchManualTest.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleRepository articleRepository;

    private final Author johnSmith = new Author("John Smith");
    private final Author johnDoe = new Author("John Doe");

    @Before
    public void before() {
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

    @After
    public void after() {
        articleRepository.deleteAll();
    }

    @Test
    public void givenArticleService_whenSaveArticle_thenIdIsAssigned() {
        final List<Author> authors = asList(new Author("John Smith"), johnDoe);

        Article article = new Article("Making Search Elastic");
        article.setAuthors(authors);
        article = articleRepository.save(article);
        assertNotNull(article.getId());
    }

    @Test
    public void givenPersistedArticles_whenSearchByAuthorsName_thenRightFound() {
        final Page<Article> articleByAuthorName = articleRepository.findByAuthorsName(johnSmith.getName(), PageRequest.of(0, 10));
        assertEquals(2L, articleByAuthorName.getTotalElements());
    }

    @Test
    public void givenCustomQuery_whenSearchByAuthorsName_thenArticleIsFound() {
        final Page<Article> articleByAuthorName = articleRepository.findByAuthorsNameUsingCustomQuery("Smith", PageRequest.of(0, 10));
        assertEquals(2L, articleByAuthorName.getTotalElements());
    }

    @Test
    public void givenTagFilterQuery_whenSearchByTag_thenArticleIsFound() {
        final Page<Article> articleByAuthorName = articleRepository.findByFilteredTagQuery("elasticsearch", PageRequest.of(0, 10));
        assertEquals(3L, articleByAuthorName.getTotalElements());
    }

    @Test
    public void givenTagFilterQuery_whenSearchByAuthorsName_thenArticleIsFound() {
        final Page<Article> articleByAuthorName = articleRepository.findByAuthorsNameAndFilteredTagQuery("Doe", "elasticsearch", PageRequest.of(0, 10));
        assertEquals(2L, articleByAuthorName.getTotalElements());
    }

    @Test
    public void givenPersistedArticles_whenUseRegexQuery_thenRightArticlesFound() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(regexpQuery("title", ".*data.*"))
                .build();

        List<Article> articles =  elasticsearchOperations.query(searchQuery,response->{
                List<Article> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    //没有数据
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }
                    //hit转换成bean
                    Map<String,Object> objectMap = searchHit.getSourceAsMap();

                    Article articleBean = new Article();
                    try {
                        BeanUtils.populate(articleBean, objectMap);
                        articleBean.setId(searchHit.getId());
                        chunk.add(articleBean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                return chunk;
            }
        );
        Assert.assertEquals(1,articles.size());
    }

    @Test
    public void givenSavedDoc_whenTitleUpdated_thenCouldFindByUpdatedTitle() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(fuzzyQuery("title", "serch"))
                .build();
        List<Article> articles =  elasticsearchOperations.query(searchQuery,response->{
                    List<Article> chunk = new ArrayList<>();
                    for (SearchHit searchHit : response.getHits()) {
                        //没有数据
                        if (response.getHits().getHits().length <= 0) {
                            return null;
                        }
                        //hit转换成bean
                        Map<String,Object> objectMap = searchHit.getSourceAsMap();

                        Article articleBean = new Article();
                        try {
                            BeanUtils.populate(articleBean, objectMap);
                            articleBean.setId(searchHit.getId());
                            chunk.add(articleBean);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return chunk;
                }
        );

        final Article article = articles.get(0);
        final String newTitle = "Getting started with Search Engines";
        article.setTitle(newTitle);
        articleRepository.save(article);

        assertEquals(newTitle, articleRepository.findById(article.getId())
                .get()
                .getTitle());
    }

    @Test
    public void givenSavedDoc_whenDelete_thenRemovedFromIndex() {
        final String articleTitle = "Spring Data Elasticsearch";

        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", articleTitle).minimumShouldMatch("75%"))
                .build();
        List<Article> articles =  elasticsearchOperations.query(searchQuery,response->{
                    List<Article> chunk = new ArrayList<>();
                    for (SearchHit searchHit : response.getHits()) {
                        //没有数据
                        if (response.getHits().getHits().length <= 0) {
                            return null;
                        }
                        //hit转换成bean
                        Map<String,Object> objectMap = searchHit.getSourceAsMap();

                        Article articleBean = new Article();
                        try {
                            BeanUtils.populate(articleBean, objectMap);
                            articleBean.setId(searchHit.getId());
                            chunk.add(articleBean);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return chunk;
                }
        );

        assertEquals(1, articles.size());
        final long count = articleRepository.count();

        articleRepository.delete(articles.get(0));

        assertEquals(count - 1, articleRepository.count());
    }

    @Test
    public void givenSavedDoc_whenOneTermMatches_thenFindByTitle() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "Search engines").operator(AND))
                .build();
        List<Article> articles =  elasticsearchOperations.query(searchQuery,response->{
                    List<Article> chunk = new ArrayList<>();
                    for (SearchHit searchHit : response.getHits()) {
                        //没有数据
                        if (response.getHits().getHits().length <= 0) {
                            return null;
                        }
                        //hit转换成bean
                        Map<String,Object> objectMap = searchHit.getSourceAsMap();

                        Article articleBean = new Article();
                        try {
                            BeanUtils.populate(articleBean, objectMap);
                            articleBean.setId(searchHit.getId());
                            chunk.add(articleBean);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return chunk;
                }
        );
        assertEquals(1, articles.size());
    }
}
