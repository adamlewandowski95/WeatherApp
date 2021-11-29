package com.adamlewandowski.repository;

import com.adamlewandowski.dao.WeatherDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherInformationRepository extends JpaRepository<WeatherDao, Long> {
    @Query(value = "SELECT * FROM weather WHERE city_name LIKE lower(concat('%', :searchTerm,'%'))", nativeQuery = true)
    List<WeatherDao> search(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT * FROM weather WHERE city_name LIKE lower(concat('%', :searchTerm,'%')) limit :startRow, :numberOfRowsToDisplay", nativeQuery = true)
    List<WeatherDao> searchForOneCityOnePage(@Param("searchTerm") String searchTerm, @Param("startRow") int startRow, @Param("numberOfRowsToDisplay") int numberOfRowsToDisplay);

    @Query(value = "SELECT count(*) FROM weather WHERE city_name LIKE lower(concat('%', :searchTerm,'%'))", nativeQuery = true)
    int checkNumberOfElements(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT * FROM weather limit  :startRow, :numberOfRowsToDisplay", nativeQuery = true)
    List<WeatherDao> searchForAllCicites(@Param("startRow") int startRow, @Param("numberOfRowsToDisplay") int numberOfRowsToDisplay);
}
