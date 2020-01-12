package cinema2.repository;

import cinema2.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepo extends JpaRepository<Seance, Long> {
}
