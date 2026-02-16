package com.example.cupom.model.dto;

import com.example.cupom.model.entity.Cupom;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CupomResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        Boolean published
) {

    public static CupomResponse fromEntity(Cupom cupom){
        return new CupomResponse(
                cupom.getId(),
                cupom.getCode(),
                cupom.getDescription(),
                cupom.getDiscountValue(),
                cupom.getExpirationDate(),
                cupom.getPublished()
        );
    }
}