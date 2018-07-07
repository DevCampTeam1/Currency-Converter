package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.model.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query(value = "SELECT * FROM hotels AS h\n" +
            "WHERE h.country_id IN \n" +
            "\t(SELECT c.id FROM countries AS c\n" +
            "\tWHERE c.currency_id = :currencyId)" +
            "AND h.price <= :price"
            , nativeQuery = true)
    List<Hotel> getAllAvailableHotels(@Param(value = "price") Double price
            , @Param(value = "currencyId") Long currencyId);
}
