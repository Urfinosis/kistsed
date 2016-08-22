package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
	List<Content> findByIdPageAndIdSite(long idPage,long idSite);
	Content findByIdPageAndTypeContent(long idPage, String typeContent);
	void deleteByIdPage(long idPage);
}
