package cinema2.repository;

import cinema2.model.CinemaProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaProgrammeRepo extends JpaRepository<CinemaProgramme, Long> {
}
