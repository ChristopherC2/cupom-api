package com.example.cupom.application.usecase;

import com.example.cupom.model.dto.CreateCupomRequest;
import com.example.cupom.model.dto.CupomResponse;
import com.example.cupom.model.entity.Cupom;
import com.example.cupom.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteCupomUseCase {

    private final CupomRepository repository;

    public void execute(UUID id){

        var cupom = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        cupom.delete();

        repository.save(cupom);
    }
}
