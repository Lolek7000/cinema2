package cinema2.controller;

import cinema2.model.CinemaProgramme;
import cinema2.repository.CinemaProgrammeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemaProgramme")
public class CinemaProgrammeController {

    private CinemaProgrammeRepo cinemaProgrammeRepo;

    @Autowired
    public CinemaProgrammeController(CinemaProgrammeRepo cinemaProgrammeRepo) {
        this.cinemaProgrammeRepo = cinemaProgrammeRepo;
    }

    @GetMapping("/getCinemaProgrammes")
    public Iterable<CinemaProgramme> getCinemaProgrammes() {
        return cinemaProgrammeRepo.findAll();
    }
}
