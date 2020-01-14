package cinema2.service.impl;

import cinema2.model.Movie;
import cinema2.repository.MovieRepo;
import cinema2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepo movieRepo;

    @Autowired
    public MovieServiceImpl(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public ResponseEntity addMovie(Movie newMovie) {
        if (newMovie.getTitle() == null || newMovie.getTitle().length() == 0) {
            return new ResponseEntity<>("title may not be null", HttpStatus.BAD_REQUEST);
        } else if (newMovie.getTitle().length() > 50) {
            return new ResponseEntity<>("title is too long", HttpStatus.BAD_REQUEST);

        } else if (movieRepo.findByTitle(newMovie.getTitle()).isPresent()) {

            Movie movie = movieRepo.findByTitle(newMovie.getTitle()).get();
            if (movie.getDirector().equals(newMovie.getDirector()) || movie.getDescription().equals(newMovie.getDescription())) {
                return new ResponseEntity<>("movie already exists", HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(movieRepo.save(newMovie), HttpStatus.OK);
    }

    public ResponseEntity getMovieById(Long movieId) {
        if (movieRepo.findById(movieId).isPresent()) {
            return new ResponseEntity(movieRepo.findById(movieId), HttpStatus.OK);
        } else {
            return new ResponseEntity("Object not found :(", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity getMovies() {
        if (movieRepo.findAll().isEmpty()) {
            return new ResponseEntity("List is empty :(", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(movieRepo.findAll(), HttpStatus.OK);
        }
    }

    public ResponseEntity deleteMovieById(Long movieId) {
        if (movieRepo.findById(movieId).isPresent()) {
            movieRepo.deleteById(movieId);
            return new ResponseEntity("Object has been deleted :)", HttpStatus.OK);
        } else {
            return new ResponseEntity("Object not found :(", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity updateMovie(Movie newMovie) {
        if (!movieRepo.findById(newMovie.getId()).isPresent()) {
            return new ResponseEntity("Object not found :(", HttpStatus.NOT_FOUND);
        } else {
            movieRepo.save(newMovie);
            return new ResponseEntity("Object has been updated :)", HttpStatus.OK);
        }
    }

    public ResponseEntity updateTitle(Long movieId, String title) {
        if (!movieRepo.findById(movieId).isPresent()) {
            return new ResponseEntity("Object not found :(", HttpStatus.NOT_FOUND);
        } else if (title.equals("") || title.equals(null)) {
            return new ResponseEntity("Title cannot be null :(", HttpStatus.BAD_REQUEST);
        } else {
            movieRepo.findById(movieId).get().setTitle(title);
            return new ResponseEntity("Title changed :)", HttpStatus.OK);
        }
    }
}
