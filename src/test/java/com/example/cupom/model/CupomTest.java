package com.example.cupom.model;

import com.example.cupom.model.entity.Cupom;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CupomTest {


    @Test
    void deveCriarCupomValido(){

        Cupom cupom = new Cupom(
                "ABC@12!",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5),
                true
        );

        assertEquals("ABC120", cupom.getCode());
    }

    @Test
    void naoDeveCriarCupomComCodigoVazio(){

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                new Cupom(
                        "",
                        "Cupom teste",
                        BigDecimal.valueOf(10),
                        LocalDate.now().plusDays(5),
                        true
                )
        );

        assertEquals("O código do cupom não pode ser vazio", ex.getMessage());
    }

    @Test
    void naoDeveCriarCupomSemDescricao(){

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                new Cupom(
                        "ABC123",
                        null,
                        BigDecimal.valueOf(10),
                        LocalDate.now().plusDays(5),
                        true
                )
        );

        assertEquals("A descrição é obrigatória", ex.getMessage());
    }

    @Test
    void naoDeveCriarCupomComDescontoMenorQueMinimo(){

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                new Cupom(
                        "ABC123",
                        "Cupom teste",
                        BigDecimal.valueOf(0.2),
                        LocalDate.now().plusDays(5),
                        true
                )
        );

        assertEquals("O valor mínimo de desconto é 0.5", ex.getMessage());
    }

    @Test
    void naoDeveCriarCupomComDataExpirada(){

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                new Cupom(
                        "ABC123",
                        "Cupom teste",
                        BigDecimal.valueOf(10),
                        LocalDate.now().minusDays(1),
                        true
                )
        );

        assertEquals("A data de expiração não pode estar no passado", ex.getMessage());
    }

    @Test
    void naoDevePermitirDeletarCupomDuasVezes(){

        Cupom cupom = new Cupom(
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5),
                true
        );

        cupom.delete();

        RuntimeException ex = assertThrows(RuntimeException.class, cupom::delete);
        assertEquals("O cupom já foi deletado", ex.getMessage());
    }
}
