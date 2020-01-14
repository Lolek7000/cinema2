package cinema2.controller;

import cinema2.model.Seance;
import cinema2.service.impl.SeanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seance")
public class SeanceController {

    private SeanceServiceImpl seanceService;

    @Autowired
    public SeanceController(SeanceServiceImpl seanceService) {
        this.seanceService = seanceService;
    }

    @PostMapping("/add")
    public ResponseEntity addSeance(@RequestBody Seance seance) {
        return seanceService.addSeance(seance);
    }

    @GetMapping("/get/{seanceId}")
    public ResponseEntity getSeanceById(@PathVariable Long seanceId) {
        return seanceService.getSeanceById(seanceId);
    }

    @GetMapping("/getAllSeances")
    public Iterable<Seance> getSeances() {
        return seanceService.getAllSeances();
    }

    @PutMapping("/update")
    public ResponseEntity updateSeance(@RequestBody Seance updatedSeance) {
        return seanceService.updateSeance(updatedSeance);
    }

    @DeleteMapping("/delete/{seanceId}")
    public boolean deleteSeanceById(@PathVariable Long seanceId) {
        return seanceService.deleteSeanceById(seanceId);
    }
}
