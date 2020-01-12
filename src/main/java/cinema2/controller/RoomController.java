package cinema2.controller;

import cinema2.model.Room;
import cinema2.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomServiceImpl roomService;

    @Autowired
    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @GetMapping("/getRooms")
    public Iterable<Room> getRooms() {
        return roomService.getRooms();
    }
}
