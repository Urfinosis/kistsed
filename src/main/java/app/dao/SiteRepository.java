package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Site;


public interface SiteRepository extends JpaRepository<Site, Long>{
	List<Site> findByUsername(String username);
	long findBySiteName(String siteName);
	Site findByIdAndUsername(long id, String username);
	void deleteSiteBySiteName(String siteName);
	void deleteSitesByUsername(String username);
}