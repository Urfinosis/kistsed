package app.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.MyTable;

public interface MyTableRepository extends JpaRepository<MyTable, Long> {
	List<MyTable> findByIdPageAndIdSite(long idPage,long idSite);
	void deleteByIdPage(long idPage);
}
