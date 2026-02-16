package com.example.cupom.application.usecase;

import com.example.cupom.model.dto.CreateCupomRequest;
import com.example.cupom.repository.CupomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCupomUseCaseTest {

    @Mock
    private CupomRepository repository;

    @InjectMocks
    private CreateCupomUseCase useCase;

    @Test
    void deveCriarCupomComSucesso(){

        CreateCupomRequest request = new CreateCupomRequest(
                "ABC@12!",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5)
        );

        var response = useCase.execute(request);

        assertNotNull(response);
        verify(repository, times(1)).save(any());
    }
}
