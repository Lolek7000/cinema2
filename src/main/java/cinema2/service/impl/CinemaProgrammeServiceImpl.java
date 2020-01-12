package cinema2.service.impl;

import cinema2.model.CinemaProgramme;
import cinema2.repository.CinemaProgrammeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CinemaProgrammeServiceImpl {

    private CinemaProgrammeRepo cinemaProgrammeRepo;

    @Autowired
    public CinemaProgrammeServiceImpl(CinemaProgrammeRepo cinemaProgrammeRepo) {
        this.cinemaProgrammeRepo = cinemaProgrammeRepo;
    }
    public CinemaProgramme addCinemaProgramme(CinemaProgramme cinemaProgramme) {
        return cinemaProgrammeRepo.save(cinemaProgramme);
    }

    public Optional<CinemaProgramme> getCinemaProgrammeById(Long cinemaProgrammeId) {
        return cinemaProgrammeRepo.findById(cinemaProgrammeId);
    }

    public Iterable<CinemaProgramme> getCinemaProgrammes() {
        return cinemaProgrammeRepo.findAll();
    }
}
