package org.example.chating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepasitory extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);


}
