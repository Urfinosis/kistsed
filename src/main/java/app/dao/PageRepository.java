package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Page;

public interface PageRepository extends JpaRepository<Page, Long>{
	List<Page> findByIdSiteAndUsername(long idSite, String username);
	List<Page> findByUsername(String username);
	Page findByIdAndIdSite(long id,long idSite);
	Page findByIdAndUsername(long id, String username);
	void deletePagesByUsername(String username);
	void deletePagesByIdSite(long idSite);
}