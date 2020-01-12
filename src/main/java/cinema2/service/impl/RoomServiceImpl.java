package cinema2.service.impl;

import cinema2.model.Cinema;
import cinema2.model.Room;
import cinema2.repository.CinemaRepo;
import cinema2.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class RoomServiceImpl {

    private RoomRepo roomRepo;

    private CinemaRepo cinemaRepo;

    @Autowired
    public RoomServiceImpl(RoomRepo roomRepo, CinemaRepo cinemaRepo) {
        this.roomRepo = roomRepo;
        this.cinemaRepo = cinemaRepo;
    }

    public ResponseEntity addRoom(Room room) {
        try {
            Cinema cinemaFromDB = cinemaRepo.getOne(room.getCinema().getId());
            if (!existInCinemaByNumber(cinemaFromDB, room)) {
                return new ResponseEntity<>(roomRepo.save(room), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Room with number " + room.getNumber() + " already exist in given cinema", HttpStatus.CONFLICT);
            }
        } catch (InvalidDataAccessApiUsageException e) {
            return new ResponseEntity<>(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                message.append(violation.getMessage());
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>("Cinema with id " + room.getCinema().getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Room> getRoomById(Long roomId) {
        return roomRepo.findById(roomId);
    }

    public Iterable<Room> getRooms() {
        return roomRepo.findAll();
    }

    private boolean existInCinemaByNumber(Cinema cinema, Room room){
        boolean existInCinemaByNumber = false;
        for (Room roomFromCinema : cinema.getRooms()) {
            if (roomFromCinema.getNumber().equals(room.getNumber())) {
                existInCinemaByNumber = true;
                break;
            }
        }
        return existInCinemaByNumber;
    }
}
