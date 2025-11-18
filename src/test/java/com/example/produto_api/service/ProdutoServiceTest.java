package com.example.produto_api.service;

import com.example.produto_api.config.exceptions.ProdutoNaoEncontradoException;
import com.example.produto_api.model.entity.Produto;
import com.example.produto_api.model.request.ProdutoRequest;
import com.example.produto_api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;
    private ProdutoRequest produtoRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(100.0);
        produto.setQuantidadeEstoque(10);

        produtoRequest = new ProdutoRequest("Produto Teste Atualizado", "Descrição Atualizada", 150.0, 20);
    }

    @Test
    void testListarTodos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        List<Produto> produtos = service.listarTodos();

        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
    }

    @Test
    void testBuscarPorId_Sucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorId_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> service.buscarPorId(2L));
    }

    @Test
    void testSalvar() {
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto resultado = service.salvar(produto);

        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.getNome());
    }

    @Test
    void testAtualizar() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto atualizado = service.atualizar(1L, produtoRequest);

        assertEquals("Produto Teste Atualizado", atualizado.getNome());
        assertEquals("Descrição Atualizada", atualizado.getDescricao());
        assertEquals(150.0, atualizado.getPreco());
        assertEquals(20, atualizado.getQuantidadeEstoque());
    }

    @Test
    void testDeletar() {
        doNothing().when(produtoRepository).deleteById(1L);

        service.deletar(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
