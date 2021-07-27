package uz.pdp.hotelgit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotelgit.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    boolean existsByName(String name);
}
