package io.github.fatec.service;

import io.github.fatec.entity.Produto;
import io.github.fatec.repository.ProdutoRepository;
import io.github.fatec.service.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {


    private final ProdutoRepository repository;

    public ProdutoServiceImpl(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Produto criar(Produto produto) {
        String id = produto.id() != null ? produto.id() : UUID.randomUUID().toString();
        Produto produtoComId = new Produto(id, produto.nome(), produto.preco());
        return repository.salvar(produtoComId);
    }

    @Override
    public Produto atualizar(Produto produto) {
        if (produto.id() == null) {
            throw new IllegalArgumentException("ID do produto é obrigatório para atualização");
        }
        return repository.atualizar(produto);
    }

    @Override
    public void deletar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
        repository.deletar(id);
    }

    @Override
    public List<Produto> listar() {
        return repository.listar();
    }

    @Override
    public Produto buscarPorId(String id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }
}
