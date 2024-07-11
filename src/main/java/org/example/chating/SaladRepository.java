package org.example.chating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaladRepository extends JpaRepository<Salad,Long> {
}
