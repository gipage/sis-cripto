package com.gino.siscripto.model.entity;

import com.gino.siscripto.model.key.HoldingKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //tabla intermedia no necesita @table
public class Holding {
    @EmbeddedId
    private HoldingKey id;
    @Column(name = "amount")
    BigDecimal amount; //cantidad expresada en unidad

    public Holding(HoldingKey holdingKey){
        this.id = holdingKey;
    }
}
