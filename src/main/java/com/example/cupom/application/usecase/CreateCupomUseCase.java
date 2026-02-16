package com.example.cupom.application.usecase;

import com.example.cupom.model.dto.CreateCupomRequest;
import com.example.cupom.model.dto.CupomResponse;
import com.example.cupom.model.entity.Cupom;
import com.example.cupom.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCupomUseCase {
    private final CupomRepository repository;

    public CupomResponse execute(CreateCupomRequest request){

        Cupom cupom = new Cupom(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                Boolean.TRUE
        );

        repository.save(cupom);
        return CupomResponse.fromEntity(cupom);
    }
}
