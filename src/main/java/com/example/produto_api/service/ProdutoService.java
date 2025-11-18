package com.example.produto_api.service;

import com.example.produto_api.config.exceptions.ProdutoNaoEncontradoException;
import com.example.produto_api.model.entity.Produto;
import com.example.produto_api.model.request.ProdutoRequest;
import com.example.produto_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, ProdutoRequest request) {

        var existente = buscarPorId(id);

        existente.setNome(request.nome());
        existente.setDescricao(request.descricao());
        existente.setPreco(request.preco());
        existente.setQuantidadeEstoque(request.quantidadeEstoque());

        return produtoRepository.save(existente);
    }

    public void deletar(Long id) {produtoRepository.deleteById(id);}
}
