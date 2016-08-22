package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
	Achievement findByUsernameAndType(String username, String type);
	List<Achievement> findByUsername(String username);
	void deleteByUsernameAndType(String username, String type);
}
