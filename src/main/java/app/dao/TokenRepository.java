package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

}
