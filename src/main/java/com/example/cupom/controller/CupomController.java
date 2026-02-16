package com.example.cupom.controller;

import com.example.cupom.application.usecase.CreateCupomUseCase;
import com.example.cupom.application.usecase.DeleteCupomUseCase;
import com.example.cupom.model.dto.CreateCupomRequest;
import com.example.cupom.model.dto.CupomResponse;
import com.example.cupom.repository.CupomRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cupons")
@Tag(name = "Cupom", description = "API para gerenciamento de cupons")
@RequiredArgsConstructor
public class CupomController {
    private final CreateCupomUseCase createCupomUseCase;
    private final DeleteCupomUseCase deleteCupomUseCase;
    private final CupomRepository cupomRepository;

    @PostMapping
    @Operation(summary = "Criar novo cupom")
    public ResponseEntity<CupomResponse> criar(@RequestBody CreateCupomRequest request){
        var response = createCupomUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cupom por ID")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        deleteCupomUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CupomResponse> buscar(@PathVariable UUID id){
        var cupom = cupomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        return ResponseEntity.ok(CupomResponse.fromEntity(cupom));
    }
}
