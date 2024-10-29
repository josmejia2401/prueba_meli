package com.josmejia2401.repository;

import com.josmejia2401.models.ReserveSeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveSeatRepository extends JpaRepository<ReserveSeatModel, Long> {
}
