package com.josmejia2401.repository;

import com.josmejia2401.models.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatModel, Long> {
}
