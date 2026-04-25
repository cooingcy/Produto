package io.github.fatec.repository;

import io.github.fatec.entity.Produto;
import io.github.fatec.repository.ProdutoRepository;
import io.github.fatec.repository.adapter.ProdutoRepositoryAdapter;
import io.github.fatec.repository.mongo.ProdutoRepositoryWithMongoDB;
import io.github.fatec.repository.orm.ProdutoOrmMongo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final ProdutoRepositoryWithMongoDB mongoRepository;

    public ProdutoRepositoryImpl(ProdutoRepositoryWithMongoDB mongoRepository) {  // ← OK!
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoOrmMongo orm = ProdutoRepositoryAdapter.toOrm(produto);
        ProdutoOrmMongo salvo = mongoRepository.save(orm);
        return ProdutoRepositoryAdapter.toEntity(salvo);
    }

    @Override
    public Produto atualizar(Produto produto) {
        return salvar(produto);
    }

    @Override
    public void deletar(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public List<Produto> listar() {
        return mongoRepository.findAll().stream()
                .map(ProdutoRepositoryAdapter::toEntity)
                .toList();
    }

    @Override
    public Optional<Produto> buscarPorId(String id) {
        return mongoRepository.findById(id)
                .map(ProdutoRepositoryAdapter::toEntity);
    }
}
