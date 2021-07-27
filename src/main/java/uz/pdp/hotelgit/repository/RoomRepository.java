package uz.pdp.hotelgit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotelgit.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    boolean existsByNumber(String number);

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
}
