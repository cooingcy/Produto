package io.github.fatec.repository.orm;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public record ItemVendaOrm(
        @Field("produtoId")
        String produtoId,
        @Field("nomeProduto")
        String nomeProduto,
        @Field("quantidade")
        Integer quantidade,
        @Field("precoUnitario")
        BigDecimal precoUnitario
) {}
