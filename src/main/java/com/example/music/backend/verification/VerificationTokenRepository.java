package com.example.music.backend.verification;

import com.example.music.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
