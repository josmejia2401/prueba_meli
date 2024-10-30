package com.josmejia2401.repository;

import com.josmejia2401.models.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {
}
