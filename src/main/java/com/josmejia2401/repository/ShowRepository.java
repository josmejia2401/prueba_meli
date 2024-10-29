package com.josmejia2401.repository;

import com.josmejia2401.models.ShowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<ShowModel, Long> {
}
