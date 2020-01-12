package cinema2.controller;

import cinema2.model.Movie;
import cinema2.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieServiceImpl movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping("get/{movieId}")
    public ResponseEntity getMovieById(@PathVariable Long movieId) {
        return movieService.getMovieById(movieId);
    }

    @GetMapping("getMovies")
    public ResponseEntity getMovies() {
        return movieService.getMovies();
    }

    @PutMapping("/update")
    public ResponseEntity updateMovie(@RequestBody Movie movie){
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity deleteMovieById(@PathVariable Long movieId){
        return movieService.deleteMovieById(movieId);
    }

    @PatchMapping("/updateTitle")
    public ResponseEntity updateTitle(@RequestParam Long movieId, @RequestParam String title){
        return movieService.updateTitle(movieId,title);
    }
}
