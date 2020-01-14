package cinema2.service.impl;

import cinema2.model.Cinema;
import cinema2.repository.CinemaRepo;
import cinema2.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Service
public class CinemaServiceImpl implements CinemaService {

    private CinemaRepo cinemaRepo;

    @Autowired
    public CinemaServiceImpl(CinemaRepo cinemaRepo) {
        this.cinemaRepo = cinemaRepo;
    }

    @Override
    public ResponseEntity addCinema(Cinema cinema) {
        try {
            return new ResponseEntity<>(cinemaRepo.save(cinema), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("given cinema name is unavailable", HttpStatus.CONFLICT);
        } catch (ConstraintViolationException e) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                message.append(violation.getMessage());
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getCinemaById(Long cinemaId) {
        return ResponseEntity.of(cinemaRepo.findById(cinemaId));
    }

    @Override
    public ResponseEntity getCinemas() {
        if (cinemaRepo.findAll().size() > 0) {
            return new ResponseEntity<>(cinemaRepo.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cinema list not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity updateCinema(Cinema updatedCinema) {

        if (cinemaRepo.findById(updatedCinema.getId()).isPresent()) {
            Cinema cinema = cinemaRepo.getOne(updatedCinema.getId());
            String message = "";

            if (updatedCinema.getName() == null || updatedCinema.getName().length() == 0) {
                message += "Cinema name may not be empty";
            } else if (updatedCinema.getName().length() > 15) {
                message += "Cinema name is too long";
            } else if (cinemaRepo.existsByName(updatedCinema.getName())) {
                message += "given cinema name is unavailable";
            } else {
                cinema.setName(updatedCinema.getName());
                cinemaRepo.save(cinema);
                return new ResponseEntity<>(cinemaRepo.save(cinema), HttpStatus.OK);
            }
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean deleteCinemaById(Long id) {
        if (cinemaRepo.findById(id).isPresent()) {
            cinemaRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
