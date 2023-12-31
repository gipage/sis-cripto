package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id_wallet", updatable = false, nullable = false)
    private UUID id;
    @Column(name="User_dni_user")
    private String userDNI; // La relacion es unidireccional de User a billetera, pq si almacenamos el usuario tenemos serialización infinita
    @Column(name = "balance_wallet")
    private BigDecimal balance; //ARS

    @OneToMany(mappedBy = "id.id_wallet") //mappedBy especifica el nombre del atributo en la entidad Holding que se utiliza para mapear esta relación
    private List<Holding> holdings;

    @OneToMany(mappedBy = "origin_wallet_id", cascade = CascadeType.ALL)
    private List<Transaction> transactions_o;
    @OneToMany(mappedBy = "destination_wallet_id", cascade = CascadeType.ALL)
    private List<Transaction> transactions_d;
}
