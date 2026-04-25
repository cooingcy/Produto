package io.github.fatec.repository.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "produtos")
public record ProdutoOrmMongo(
        @Id
        String id,
        @Field("nome")
        String nome,
        @Field("preco")
        BigDecimal preco
) {}
