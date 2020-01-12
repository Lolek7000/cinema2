package cinema2.controller;

import cinema2.model.Cinema;
import cinema2.service.impl.CinemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    private CinemaServiceImpl cinemaService;

    @Autowired
    public CinemaController(CinemaServiceImpl cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("/add")
    public ResponseEntity addCinema(@RequestBody Cinema cinema) {
        return cinemaService.addCinema(cinema);
    }

    @GetMapping("/get/{cinemaId}")
    public ResponseEntity getCinemaById(@PathVariable Long cinemaId) {
        return cinemaService.getCinemaById(cinemaId);
    }

    @GetMapping("/getCinemas")
    public ResponseEntity getCinemas() {
        return cinemaService.getCinemas();
    }

    @PutMapping("/update")
    public ResponseEntity updateCinema(@RequestBody Cinema updatedCinema) {
        return cinemaService.updateCinema(updatedCinema);
    }

    @DeleteMapping("/delete/{cinemaId}")
    public ResponseEntity deleteCinemaById(@PathVariable Long cinemaId) {
       return (cinemaService.deleteCinemaById(cinemaId)) ? new ResponseEntity<>("Success! cinema has been deleted", HttpStatus.OK) : new ResponseEntity<>("Cinema with given id does not exist", HttpStatus.NOT_FOUND);
    }
}
