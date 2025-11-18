package com.example.produto_api.controller;

import com.example.produto_api.mapper.ProdutoMapper;
import com.example.produto_api.model.request.ProdutoRequest;
import com.example.produto_api.model.response.ProdutoResponse;
import com.example.produto_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoMapper mapper;

    public ProdutoController(ProdutoService service, ProdutoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProdutoResponse>> listar() {
        var produtos = service.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoResponse> buscar(@PathVariable Long id) {
        var produto = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(produto));
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto")
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest request) {
        var produto = mapper.toEntity(request);
        var salvo = service.salvar(produto);
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto existente")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoRequest request) {

        var atualizado = service.atualizar(id, request);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um produto")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
