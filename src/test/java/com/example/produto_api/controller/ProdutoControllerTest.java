package com.example.produto_api.controller;

import com.example.produto_api.mapper.ProdutoMapper;
import com.example.produto_api.model.entity.Produto;
import com.example.produto_api.model.request.ProdutoRequest;
import com.example.produto_api.model.response.ProdutoResponse;
import com.example.produto_api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {
    @Mock
    private ProdutoService service;

    @Mock
    private ProdutoMapper mapper;

    @InjectMocks
    private ProdutoController controller;

    private Produto produto;
    private ProdutoResponse produtoResponse;
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

        produtoRequest = new ProdutoRequest("Produto Teste", "Descrição Teste", 100.0, 10);
        produtoResponse = new ProdutoResponse(1L, "Produto Teste", "Descrição Teste", 100.0, 10);
    }

    @Test
    void testListar() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        when(service.listarTodos()).thenReturn(produtos);
        when(mapper.toResponse(produto)).thenReturn(produtoResponse);

        ResponseEntity<List<ProdutoResponse>> response = controller.listar();

        assertEquals(1, response.getBody().size());
        assertEquals("Produto Teste", response.getBody().get(0).nome());
    }

    @Test
    void testBuscar() {
        when(service.buscarPorId(1L)).thenReturn(produto);
        when(mapper.toResponse(produto)).thenReturn(produtoResponse);

        ResponseEntity<ProdutoResponse> response = controller.buscar(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Produto Teste", response.getBody().nome());
    }

    @Test
    void testCriar() {
        when(mapper.toEntity(produtoRequest)).thenReturn(produto);
        when(service.salvar(produto)).thenReturn(produto);
        when(mapper.toResponse(produto)).thenReturn(produtoResponse);

        ResponseEntity<ProdutoResponse> response = controller.criar(produtoRequest);

        assertNotNull(response.getBody());
        assertEquals("Produto Teste", response.getBody().nome());
    }

    @Test
    void testAtualizar() {
        when(service.atualizar(1L, produtoRequest)).thenReturn(produto);
        when(mapper.toResponse(produto)).thenReturn(produtoResponse);

        ResponseEntity<ProdutoResponse> response = controller.atualizar(1L, produtoRequest);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    void testDeletar() {
        doNothing().when(service).deletar(1L);

        ResponseEntity<Void> response = controller.deletar(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(service, times(1)).deletar(1L);
    }
}
