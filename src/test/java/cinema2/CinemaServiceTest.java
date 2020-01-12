package cinema2;

import cinema2.model.Cinema;
import cinema2.repository.CinemaRepo;
import cinema2.service.impl.CinemaServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.groups.Default;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CinemaServiceTest {

    @Mock
    private CinemaRepo cinemaRepo;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    private LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    @Test
    public void shouldAddCinemaWhenRepositoryDoNotThrowException() {
        when(cinemaRepo.save(any(Cinema.class))).thenReturn(new Cinema());
        Assert.assertEquals(Cinema.class, Objects.requireNonNull(cinemaService.addCinema(new Cinema()).getBody()).getClass());
    }

    @Test
    public void shouldReturnConflictWhenRepositoryThrowDataIntegrityViolationException() {
        when(cinemaRepo.save(any(Cinema.class))).thenThrow(DataIntegrityViolationException.class);
        Assert.assertEquals(new ResponseEntity<>("given cinema name is unavailable", HttpStatus.CONFLICT), cinemaService.addCinema(new Cinema()));
    }

    @Test
    public void shouldReturnBadRequestWhenNameIsLongerThan15() {
        validator.afterPropertiesSet();
        Cinema cinema = new Cinema("TooLongCinemaName");
        Set<ConstraintViolation<Cinema>> violations = validator.validate(cinema, Default.class);
        when(cinemaRepo.save(any(Cinema.class))).thenThrow(new ConstraintViolationException(violations));
        Assert.assertEquals(new ResponseEntity<>("Cinema name is too long", HttpStatus.BAD_REQUEST), cinemaService.addCinema(new Cinema()));
    }

    @Test
    public void shouldReturnBadRequestWhenNameIsNullOrBlank() {
        validator.afterPropertiesSet();
        Cinema blankCinema = new Cinema("");
        Cinema nullCinema = new Cinema();
        Set<ConstraintViolation<Cinema>> violations = validator.validate(blankCinema, Default.class);
        when(cinemaRepo.save(any(Cinema.class))).thenThrow(new ConstraintViolationException(violations));
        Assert.assertEquals(new ResponseEntity<>("Cinema name may not be empty", HttpStatus.BAD_REQUEST), cinemaService.addCinema(blankCinema));
        Set<ConstraintViolation<Cinema>> nullViolations = validator.validate(nullCinema, Default.class);
        when(cinemaRepo.save(any(Cinema.class))).thenThrow(new ConstraintViolationException(nullViolations));
        Assert.assertEquals(new ResponseEntity<>("Cinema name may not be empty", HttpStatus.BAD_REQUEST), cinemaService.addCinema(nullCinema));
    }

    @Test
    public void shouldReturnCinemaWhenExistsInDatabase() {
        Cinema cinema = new Cinema("Lublin");
        when(cinemaRepo.findById(1L)).thenReturn(Optional.of(cinema));
        Assert.assertEquals(new ResponseEntity<>(cinema, HttpStatus.OK), cinemaService.getCinemaById(1L));
    }

    @Test
    public void shouldReturnCinemaNotFoundWhenCinemaDoesNotExist() {
        when(cinemaRepo.findById(1L)).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), cinemaService.getCinemaById(1L));
    }

    @Test
    public void shouldReturnTrueWhenExistsCinemaHasBeenDeleted() {
        when(cinemaRepo.findById(1L)).thenReturn(Optional.of(new Cinema()));
        Assert.assertTrue(cinemaService.deleteCinemaById(1L));
    }

    @Test
    public void shouldReturnFalseWhenCinemaToDeleteDoesNotExist() {
        when(cinemaRepo.findById(1L)).thenReturn(Optional.empty());
        Assert.assertFalse(cinemaService.deleteCinemaById(1L));
    }

    @Test
    public void shouldReturnIterableWhenSizeOfListCinemaIsGreaterBy0() {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema());
        when(cinemaRepo.findAll()).thenReturn(cinemas);
        Assert.assertNotEquals(String.class, Objects.requireNonNull(cinemaService.getCinemas().getBody()).getClass());

    }

    @Test
    public void shouldReturnNotFoundWhenSizeOfCinemaListIsNotGreaterBy0() {
        when(cinemaRepo.findAll()).thenReturn(new ArrayList<>());
        Assert.assertEquals(new ResponseEntity<>("Cinema list not found", HttpStatus.NOT_FOUND), cinemaService.getCinemas());
    }
}
