package com.colak.springtutorial.repository;

import com.colak.springtutorial.jpa.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {

}
