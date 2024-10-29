package com.josmejia2401.repository;

import com.josmejia2401.models.FunctionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionModel, Long> {
}
