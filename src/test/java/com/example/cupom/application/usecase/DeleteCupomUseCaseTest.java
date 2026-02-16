package com.example.cupom.application.usecase;

import com.example.cupom.model.entity.Cupom;
import com.example.cupom.repository.CupomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCupomUseCaseTest {
    @Mock
    private CupomRepository repository;

    @InjectMocks
    private DeleteCupomUseCase useCase;

    @Test
    void deveDeletarCupomComSucesso(){

        Cupom cupom = new Cupom(
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5),
                true
        );

        when(repository.findByIdAndDeletedFalse(any()))
                .thenReturn(Optional.of(cupom));

        useCase.execute(UUID.randomUUID());

        verify(repository, times(1)).save(cupom);
    }
}
