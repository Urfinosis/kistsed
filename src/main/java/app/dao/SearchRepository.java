package app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import app.model.Content;
import app.model.Page;
import app.model.Site;
import app.model.Tag;
import app.model.User;

@Repository("searchDAO")
@Transactional
public class SearchRepository {
	@PersistenceContext
	private EntityManager em;
	
	public List<Tag> findTags(String search){
		FullTextEntityManager fullTextEntityManager =
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(Tag.class).get();
			org.apache.lucene.search.Query luceneQuery = qb
			  .keyword()
			  .onFields("tag")
			  .matching(search)
			  .createQuery();
			javax.persistence.Query jpaQuery =
			    fullTextEntityManager.createFullTextQuery(luceneQuery, Tag.class);
			List<Tag> result = jpaQuery.getResultList();
			return result;
	}
	
	public List<Page> findPages(String search){
		FullTextEntityManager fullTextEntityManager =
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(Page.class).get();
			org.apache.lucene.search.Query luceneQuery = qb
			  .keyword()
			  .onFields("pageName")
			  .matching(search)
			  .createQuery();
			javax.persistence.Query jpaQuery =
			    fullTextEntityManager.createFullTextQuery(luceneQuery, Page.class);
			List<Page> result = jpaQuery.getResultList();
			return result;
	}
	
	public List<Site> findSites(String search){
		FullTextEntityManager fullTextEntityManager =
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(Site.class).get();
			org.apache.lucene.search.Query luceneQuery = qb
			  .keyword()
			  .onFields("siteName")
			  .matching(search)
			  .createQuery();
			javax.persistence.Query jpaQuery =
			    fullTextEntityManager.createFullTextQuery(luceneQuery, Site.class);
			List<Site> result = jpaQuery.getResultList();
			return result;
	}
	
	public List<Content> findContent(String search){
		FullTextEntityManager fullTextEntityManager =
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(Content.class).get();
			org.apache.lucene.search.Query luceneQuery = qb
			  .keyword()
			  .onFields("content")
			  .matching(search)
			  .createQuery();
			javax.persistence.Query jpaQuery =
			    fullTextEntityManager.createFullTextQuery(luceneQuery, Content.class);
			List<Content> result = jpaQuery.getResultList();
			return result;
	}
	
	public List<User> findUser(String search){
		FullTextEntityManager fullTextEntityManager =
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(User.class).get();
			org.apache.lucene.search.Query luceneQuery = qb
			  .keyword()
			  .onFields("username")
			  .matching(search)
			  .createQuery();
			javax.persistence.Query jpaQuery =
			    fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);
			List<User> result = jpaQuery.getResultList();
			System.out.println("size dsa"+result.size());
			return result;
	}
	
}
