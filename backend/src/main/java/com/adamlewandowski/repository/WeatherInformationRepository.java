package com.adamlewandowski.repository;

import com.adamlewandowski.dao.WeatherDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInformationRepository extends JpaRepository<WeatherDao, Long> {

}
