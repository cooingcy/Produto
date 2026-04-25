package io.github.fatec.service;

import io.github.fatec.entity.ItemVenda;
import io.github.fatec.entity.Venda;
import io.github.fatec.repository.ProdutoRepository;
import io.github.fatec.repository.VendaRepository;
import io.github.fatec.service.VendaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaServiceImpl(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Venda realizarVenda(Venda vendaRequest) {
        List<ItemVenda> itensValidos = validarEPreencherItens(vendaRequest.itens());

        BigDecimal total = itensValidos.stream()
                .map(ItemVenda::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer proximoNumero = gerarProximoNumero();
        String nomeCliente = buscarNomeCliente(vendaRequest.clienteId());

        Venda vendaCompleta = new Venda(
                UUID.randomUUID().toString(),
                proximoNumero,
                vendaRequest.clienteId(),
                nomeCliente,
                itensValidos,
                total,
                LocalDateTime.now()
        );

        return vendaRepository.salvar(vendaCompleta);
    }

    @Override
    public List<Venda> listarVendas() {
        return vendaRepository.listar();
    }

    @Override
    public Venda consultarVendaPorNumero(Integer numero) {
        return vendaRepository.buscarPorNumero(numero)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com número: " + numero));
    }

    @Override
    public List<Venda> listarComprasPorCliente(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        return vendaRepository.listarPorCliente(clienteId);
    }

    private List<ItemVenda> validarEPreencherItens(List<ItemVenda> itens) {
        return itens.stream()
                .map(this::validarItem)
                .toList();
    }

    private ItemVenda validarItem(ItemVenda item) {
        var produto = produtoRepository.buscarPorId(item.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.produtoId()));

        if (item.precoUnitario() == null) {
            return new ItemVenda(
                    item.produtoId(),
                    produto.nome(),
                    item.quantidade(),
                    produto.preco(),
                    null
            );
        }

        if (!produto.preco().equals(item.precoUnitario())) {
            throw new RuntimeException("Preço divergente para produto: " + item.produtoId());
        }

        return new ItemVenda(
                item.produtoId(),
                produto.nome(),
                item.quantidade(),
                item.precoUnitario(),
                null
        );
    }

    private Integer gerarProximoNumero() {
        return (int) (System.currentTimeMillis() % 10000);
    }

    private String buscarNomeCliente(String clienteId) {
        return "Cliente " + clienteId;
    }
}