package uz.pdp.hotelgit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotelgit.entity.Hotel;
import uz.pdp.hotelgit.entity.Room;
import uz.pdp.hotelgit.payload.RoomDto;
import uz.pdp.hotelgit.repository.HotelRepository;
import uz.pdp.hotelgit.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @PostMapping
    public String add(@RequestBody RoomDto roomDto) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            boolean existsByNumber = roomRepository.existsByNumber(roomDto.getNumber());
            if (existsByNumber)
                return "There is such a digital room";
            Room room = new Room(null, roomDto.getNumber(), roomDto.getFloor(), roomDto.getSize(), hotel);
            roomRepository.save(room);
            return "Room added";
        }
        return "Hotel not found";
    }

    @GetMapping
    public List<Room> get() {
        List<Room> roomRepositoryAll = roomRepository.findAll();
        return roomRepositoryAll;
    }

    @GetMapping("/byHotelId/{hotelId}")
    public Page<Room> getByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> allByHotelId = roomRepository.findAllByHotelId(hotelId, pageable);
        return allByHotelId;
    }

    @PutMapping("{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            Optional<Room> optionalRoom = roomRepository.findById(id);
            if (optionalRoom.isPresent()) {
                Room room = optionalRoom.get();
                boolean existsByNumber = roomRepository.existsByNumber(roomDto.getNumber());
                if (existsByNumber)
                    return "There is such a digital room";
                room.setFloor(roomDto.getFloor());
                room.setNumber(roomDto.getNumber());
                room.setSize(roomDto.getSize());
                room.setHotel(hotel);
                roomRepository.save(room);
                return "Room edited";
            }
            return "Room not found";
        }
        return "Hotel not found";
    }

}
