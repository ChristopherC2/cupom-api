package com.example.cupom.repository;

import com.example.cupom.model.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CupomRepository extends JpaRepository<Cupom, UUID> {
    Optional<Cupom> findByIdAndDeletedFalse(UUID id);
}