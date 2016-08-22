package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.NewSite;

public interface NewSiteRepository extends JpaRepository<NewSite, Long>{
	NewSite findByPosition(int position);
	NewSite findBySiteId(long siteId);
}
