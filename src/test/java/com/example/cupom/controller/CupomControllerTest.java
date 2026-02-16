package com.example.cupom.controller;

import com.example.cupom.application.usecase.CreateCupomUseCase;
import com.example.cupom.application.usecase.DeleteCupomUseCase;
import com.example.cupom.model.dto.CreateCupomRequest;
import com.example.cupom.model.dto.CupomResponse;
import com.example.cupom.model.entity.Cupom;
import com.example.cupom.repository.CupomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CupomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCupomUseCase createCupomUseCase;

    @MockBean
    private DeleteCupomUseCase deleteCupomUseCase;

    @MockBean
    private CupomRepository cupomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarCupomViaEndpoint() throws Exception {

        CreateCupomRequest request = new CreateCupomRequest(
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5)
        );

        CupomResponse response = new CupomResponse(
                UUID.randomUUID(),
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5),
                true
        );

        when(createCupomUseCase.execute(request)).thenReturn(response);

        mockMvc.perform(post("/cupons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveDeletarCupomViaEndpoint() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/cupons/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteCupomUseCase, times(1)).execute(id);
    }

    @Test
    void deveBuscarCupomPorIdViaEndpoint() throws Exception {

        UUID id = UUID.randomUUID();

        Cupom cupom = new Cupom(
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(5),
                true
        );

        when(cupomRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(cupom));

        mockMvc.perform(get("/cupons/{id}", id))
                .andExpect(status().isOk());
    }
}
