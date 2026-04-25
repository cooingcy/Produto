package io.github.fatec.controller;

import io.github.fatec.controller.adapter.ProdutoControllerAdapter;
import io.github.fatec.controller.dto.request.VendaRequest;
import io.github.fatec.controller.dto.response.VendaResponse;
import io.github.fatec.service.VendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VendaResponse> realizarVenda(@RequestBody VendaRequest request) {
        var venda = ProdutoControllerAdapter.castVenda(request);
        var vendaRealizada = service.realizarVenda(venda);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoControllerAdapter.toVendaResponse(vendaRealizada));
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        var vendas = service.listarVendas().stream()
                .map(ProdutoControllerAdapter::toVendaResponse)
                .toList();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<VendaResponse> consultarPorNumero(@PathVariable Integer numero) {
        var venda = service.consultarVendaPorNumero(numero);
        return ResponseEntity.ok(ProdutoControllerAdapter.toVendaResponse(venda));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VendaResponse>> listarComprasPorCliente(@PathVariable String clienteId) {
        var compras = service.listarComprasPorCliente(clienteId).stream()
                .map(ProdutoControllerAdapter::toVendaResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }
}
