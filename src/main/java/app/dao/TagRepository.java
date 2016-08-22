package app.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Tag;


public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByIdSite(long idSite);
	void deleteByIdSite(long idSite);
}
