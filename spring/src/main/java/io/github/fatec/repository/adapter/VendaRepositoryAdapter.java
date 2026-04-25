package io.github.fatec.repository.adapter;

import io.github.fatec.entity.ItemVenda;
import io.github.fatec.entity.Venda;
import io.github.fatec.repository.orm.ItemVendaOrm;
import io.github.fatec.repository.orm.VendaOrmMongo;

import java.math.BigDecimal;
import java.util.List;

public class VendaRepositoryAdapter {

    private VendaRepositoryAdapter() {}

    public static VendaOrmMongo toOrm(Venda entity) {
        List<ItemVendaOrm> itensOrm = entity.itens().stream()
                .map(i -> new ItemVendaOrm(
                        i.produtoId(),
                        i.nomeProduto(),
                        i.quantidade(),
                        i.precoUnitario()
                ))
                .toList();

        return new VendaOrmMongo(
                entity.id(),
                entity.numero(),
                entity.clienteId(),
                entity.nomeCliente(),
                itensOrm,
                entity.total(),
                entity.dataVenda()
        );
    }

    public static Venda toEntity(VendaOrmMongo orm) {
        List<ItemVenda> itens = orm.itens().stream()
                .map(i -> new ItemVenda(
                        i.produtoId(),
                        i.nomeProduto(),
                        i.quantidade(),
                        i.precoUnitario(),
                        i.precoUnitario().multiply(BigDecimal.valueOf(i.quantidade()))
                ))
                .toList();

        return new Venda(
                orm.id(),
                orm.numero(),
                orm.clienteId(),
                orm.nomeCliente(),
                itens,
                orm.total(),
                orm.dataVenda()
        );
    }
}
