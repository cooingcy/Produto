package io.github.fatec.repository;

import io.github.fatec.entity.Venda;
import io.github.fatec.repository.VendaRepository;
import io.github.fatec.repository.adapter.VendaRepositoryAdapter;
import io.github.fatec.repository.mongo.VendaRepositoryWithMongoDB;
import io.github.fatec.repository.orm.VendaOrmMongo;
import org.springframework.stereotype.Repository;  // ← @Repository!

import java.util.List;
import java.util.Optional;

@Repository
public class VendaRepositoryImpl implements VendaRepository {
    private final VendaRepositoryWithMongoDB mongoRepository;

    public VendaRepositoryImpl(VendaRepositoryWithMongoDB mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Venda salvar(Venda venda) {
        VendaOrmMongo orm = VendaRepositoryAdapter.toOrm(venda);
        VendaOrmMongo salvo = mongoRepository.save(orm);
        return VendaRepositoryAdapter.toEntity(salvo);
    }

    @Override
    public Optional<Venda> buscarPorNumero(Integer numero) {
        return mongoRepository.findByNumero(numero)
                .map(VendaRepositoryAdapter::toEntity);
    }

    @Override
    public List<Venda> listar() {
        return mongoRepository.findAll().stream()
                .map(VendaRepositoryAdapter::toEntity)
                .toList();
    }

    @Override
    public List<Venda> listarPorCliente(String clienteId) {
        return mongoRepository.findByClienteId(clienteId).stream()
                .map(VendaRepositoryAdapter::toEntity)
                .toList();
    }
}