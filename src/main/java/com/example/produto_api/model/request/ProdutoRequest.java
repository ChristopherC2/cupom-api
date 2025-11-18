package com.example.produto_api.model.request;

public record ProdutoRequest(
        String nome,
        String descricao,
        Double preco,
        Integer quantidadeEstoque
) {}
