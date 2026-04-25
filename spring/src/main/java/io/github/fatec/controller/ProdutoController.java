package io.github.fatec.controller;

import io.github.fatec.controller.adapter.ProdutoControllerAdapter;
import io.github.fatec.controller.dto.request.ProdutoRequest;
import io.github.fatec.controller.dto.request.ProdutoUpdateRequest;
import io.github.fatec.controller.dto.response.ProdutoResponse;
import io.github.fatec.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest request) {
        var produto = ProdutoControllerAdapter.cast(request);
        var criado = service.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoControllerAdapter.toResponse(criado));
    }

    @PutMapping
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoUpdateRequest request) {
        var produto = ProdutoControllerAdapter.cast(request);
        var atualizado = service.atualizar(produto);
        return ResponseEntity.ok(ProdutoControllerAdapter.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        var produtos = service.listar().stream()
                .map(ProdutoControllerAdapter::toResponse)
                .toList();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable String id) {
        var produto = service.buscarPorId(id);
        return ResponseEntity.ok(ProdutoControllerAdapter.toResponse(produto));
    }
}