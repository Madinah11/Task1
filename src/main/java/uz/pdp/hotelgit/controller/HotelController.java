package uz.pdp.hotelgit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotelgit.entity.Hotel;
import uz.pdp.hotelgit.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @PostMapping
    public String add(@RequestBody Hotel hotel){
        boolean existsByName = hotelRepository.existsByName(hotel.getName());
        if (existsByName)
            return "This Hotel already exist";
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    @GetMapping
    public List<Hotel> get(){
        List<Hotel> allHotels = hotelRepository.findAll();
        return allHotels;
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable Integer id){
        Optional<Hotel> hotelbyId = hotelRepository.findById(id);
       if (hotelbyId.isPresent()){
           Hotel hotel = hotelbyId.get();
           return hotel;
       }
       return new Hotel();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        hotelRepository.deleteById(id);
        return "Hotel deleted";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel1 = optionalHotel.get();
            boolean existsByName = hotelRepository.existsByName(hotel.getName());
            if (existsByName)
                return "This Hotel already exist";
            hotel1.setName(hotel.getName());
            hotelRepository.save(hotel1);
            return "Hotel edited";
        }
        return "Hotel not found";
    }

}
