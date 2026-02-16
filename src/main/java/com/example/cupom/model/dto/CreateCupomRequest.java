package com.example.cupom.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCupomRequest(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate
) {}