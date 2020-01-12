package cinema2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Transactional
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cinema cinema;

    @Column(name = "number")
    @NotNull(message = "Number must not be null. ")
    @Min(value = 1L, message = "Number of room must be greater by 0 and smaller by 36. ")
    private Integer number;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Seance> seances = new ArrayList<>();

    @Column(name = "numberOfRows")
    @NotNull(message = "Rows must not be null. ")
    @Min(value = 7L, message = "Amount of rows must be greater by 6 and smaller by 36. ")
    @Max(value = 35L, message = "Amount of rows must be greater by 6 and smaller by 36. ")
    private Integer rows;

    @Column(name = "numberOfPlaces")
    @NotNull(message = "Places must not be null. ")
    @Min(value = 6L, message = "Amount of places must be greater by 5 and smaller by 36. ")
    @Max(value = 35L, message = "Amount of places must be greater by 5 and smaller by 36. ")
    private Integer places;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    List<Seat> seatList = new ArrayList<>();

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = addSeats();
    }

    public void reserve(List<Integer> list){
        for (int i=0;i<list.size();i++){
            seatList.get(list.get(i)).setReserved(true);
        }
    }

    public ArrayList<Seat> addSeats(){
        ArrayList<Seat> seats = new ArrayList<>();
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.places;j++){
                seats.add(new Seat((char)(i+65),j+1));
            }
        }
        return seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(cinema, room.cinema) &&
                Objects.equals(number, room.number) &&
                Objects.equals(seances, room.seances) &&
                Objects.equals(rows, room.rows) &&
                Objects.equals(places, room.places) &&
                Objects.equals(seatList, room.seatList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cinema, number, seances, rows, places, seatList);
    }
}
