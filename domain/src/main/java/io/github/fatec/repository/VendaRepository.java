package io.github.fatec.repository;

import io.github.fatec.entity.Venda;

import java.util.List;
import java.util.Optional;

public interface VendaRepository {
    Venda salvar(Venda venda);
    Optional<Venda> buscarPorNumero(Integer numero);
    List<Venda> listar();
    List<Venda> listarPorCliente(String clienteId);
}
