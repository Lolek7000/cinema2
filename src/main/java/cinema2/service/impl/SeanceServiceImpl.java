package cinema2.service.impl;

import cinema2.model.CinemaProgramme;
import cinema2.model.Movie;
import cinema2.model.Room;
import cinema2.model.Seance;
import cinema2.repository.CinemaProgrammeRepo;
import cinema2.repository.MovieRepo;
import cinema2.repository.RoomRepo;
import cinema2.repository.SeanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SeanceServiceImpl {

    private SeanceRepo seanceRepo;
    private RoomRepo roomRepo;
    private MovieRepo movieRepo;
    private CinemaProgrammeRepo cinemaProgrammeRepo;

    @Autowired
    public SeanceServiceImpl(SeanceRepo seanceRepo, RoomRepo roomRepo, MovieRepo movieRepo, CinemaProgrammeRepo cinemaProgrammeRepo) {
        this.seanceRepo = seanceRepo;
        this.roomRepo = roomRepo;
        this.movieRepo = movieRepo;
        this.cinemaProgrammeRepo = cinemaProgrammeRepo;
    }

    public ResponseEntity addSeance(Seance seance) {
        ResponseEntity<String> resultOfValidation = DataValidation(seance);
        if (resultOfValidation.getStatusCode().equals(HttpStatus.OK)) {
            Movie movie = movieRepo.getOne(seance.getMovie().getId());
            Room room = roomRepo.getOne(seance.getRoom().getId());
            CinemaProgramme cinemaProgramme = cinemaProgrammeRepo.getOne(room.getCinema().getCinemaProgramme().getId());
            return new ResponseEntity<>(seanceRepo.save(new Seance(room, movie, cinemaProgramme, seance.getDate())), HttpStatus.OK);
        } else {
            return resultOfValidation;
        }
    }

    public ResponseEntity getSeanceById(Long seanceId) {
        return ResponseEntity.of(seanceRepo.findById(seanceId));
    }

    public Iterable<Seance> getAllSeances() {
        return seanceRepo.findAll();
    }

    public ResponseEntity updateSeance(Seance updatedSeance) {
        if (seanceRepo.findById(updatedSeance.getId()).isPresent()) {
            Seance seanceFromDB = seanceRepo.getOne(updatedSeance.getId());
            ResponseEntity<String> resultOfValidation = DataValidation(updatedSeance);
            if (resultOfValidation.getStatusCode().equals(HttpStatus.OK)) {
                Movie movie = movieRepo.getOne(updatedSeance.getMovie().getId());
                Room room = roomRepo.getOne(updatedSeance.getRoom().getId());
                CinemaProgramme cinemaProgramme = cinemaProgrammeRepo.getOne(room.getCinema().getCinemaProgramme().getId());
                seanceFromDB.setMovie(movie);
                seanceFromDB.setRoom(room);
                seanceFromDB.setCinemaProgramme(cinemaProgramme);
                return new ResponseEntity<>(seanceRepo.save(seanceFromDB), HttpStatus.OK);
            } else {
                return resultOfValidation;
            }
        } else {
            return new ResponseEntity<>("Seance with id " + updatedSeance.getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteSeanceById(Long seanceId) {
        if (!seanceRepo.findById(seanceId).isPresent()) {
            return false;
        }
        seanceRepo.deleteById(seanceId);
        return true;
    }

    private ResponseEntity<String> DataValidation(Seance seance) {
        if (!movieRepo.findById(seance.getMovie().getId()).isPresent()) {
            return new ResponseEntity<>("Movie with id " + seance.getMovie().getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }
        if (!roomRepo.findById(seance.getRoom().getId()).isPresent()) {
            return new ResponseEntity<>("Room with id " + seance.getRoom().getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }
        if (seance.getDate().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Given date: " + seance.getDate() + " is from the past.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
