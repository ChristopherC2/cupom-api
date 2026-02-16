package com.example.cupom.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private String description;
    private BigDecimal discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean deleted = false;

    public Cupom(String code,
                  String description,
                  BigDecimal discountValue,
                  LocalDate expirationDate,
                  Boolean published) {

        validateRawCode(code);
        this.code = sanitizeCode(code);
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;

        validate();
    }

    private void validate() {
        validateDescription();
        validateDiscount();
        validateExpirationDate();
    }

    private void validateDescription() {
        if(description == null || description.isBlank()){
            throw new RuntimeException("A descrição é obrigatória");
        }
    }

    private void validateDiscount() {
        if(discountValue == null || discountValue.compareTo(BigDecimal.valueOf(0.5)) < 0)
            throw new RuntimeException("O valor mínimo de desconto é 0.5");
    }

    private void validateExpirationDate() {
        if(expirationDate == null || expirationDate.isBefore(LocalDate.now()))
            throw new RuntimeException("A data de expiração não pode estar no passado");
    }
    private String sanitizeCode(String rawCode){
        String sanitized = rawCode.replaceAll("[^a-zA-Z0-9]", "");

        if(sanitized.length() > 6){
            sanitized = sanitized.substring(0,6);
        }

        if(sanitized.length() < 6){
            sanitized = String.format("%-6s", sanitized)
                    .replace(' ', '0');
        }
        return sanitized;
    }

    public void delete(){

        if(this.deleted){
            throw new RuntimeException("O cupom já foi deletado");
        }

        this.deleted = true;
    }

    private void validateRawCode(String rawCode){

        if(rawCode == null || rawCode.isBlank()){
            throw new RuntimeException("O código do cupom não pode ser vazio");
        }

        String onlyAlphanumeric = rawCode.replaceAll("[^a-zA-Z0-9]", "");

        if(onlyAlphanumeric.isBlank()){
            throw new RuntimeException("O código do cupom deve conter ao menos um caractere alfanumérico");
        }
    }
}
