package com.adamlewandowski.WeatherApp.repository;

import com.adamlewandowski.WeatherApp.dao.WeatherDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInformationRepository extends JpaRepository<WeatherDao, Long> {

}
