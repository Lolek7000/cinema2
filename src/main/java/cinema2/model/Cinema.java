package cinema2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cinema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true)
    @Size(max = 15, message = "Cinema name is too long")
    @NotBlank(message = "Cinema name must not be empty")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(updatable = false)
    private CinemaProgramme cinemaProgramme = new CinemaProgramme();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema")
    private List<Room> rooms = new ArrayList<>();

    public Cinema() {
    }

    public Cinema(@Size(max = 15, message = "Cinema name is too long") @NotBlank(message = "Cinema name may not be empty") String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CinemaProgramme getCinemaProgramme() {
        return cinemaProgramme;
    }

    public void setCinemaProgramme(CinemaProgramme cinemaProgramme) {
        this.cinemaProgramme = cinemaProgramme;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
