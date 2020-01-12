package cinema2.service.impl;

import cinema2.model.Seance;
import cinema2.repository.RoomRepo;
import cinema2.repository.SeanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SeanceServiceImpl {

    private SeanceRepo seanceRepo;
    private RoomRepo roomRepo;

    @Autowired
    public SeanceServiceImpl(SeanceRepo seanceRepo, RoomRepo roomRepo) {
        this.seanceRepo = seanceRepo;
        this.roomRepo = roomRepo;
    }

    public ResponseEntity addSeance(Seance seance) {
            return new ResponseEntity<>(seanceRepo.save(seance), HttpStatus.OK);
    }

    public ResponseEntity getSeanceById(Long seanceId) {
        return ResponseEntity.of(seanceRepo.findById(seanceId));
    }

    public Iterable<Seance> getSeances(){
        return seanceRepo.findAll();
    }
}
