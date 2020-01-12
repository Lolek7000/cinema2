package cinema2.service;

import cinema2.model.Cinema;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CinemaService {

    ResponseEntity addCinema(Cinema cinema);

    ResponseEntity getCinemas();

    ResponseEntity getCinemaById(Long id);

    ResponseEntity updateCinema(Cinema updatedCinema);

    boolean deleteCinemaById(Long id);


}
