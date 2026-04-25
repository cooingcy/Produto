package io.github.fatec.repository.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "vendas")
public record VendaOrmMongo(
        @Id
        String id,
        @Field("numero")
        Integer numero,
        @Field("clienteId")
        String clienteId,
        @Field("nomeCliente")
        String nomeCliente,
        @Field("itens")
        List<ItemVendaOrm> itens,
        @Field("total")
        BigDecimal total,
        @Field("dataVenda")
        LocalDateTime dataVenda
) {}
