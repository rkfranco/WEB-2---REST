package com.furb.controle.model;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class MarcaDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long MARCA_ID;

    @Column
    private String razaoSocial;

    @Column
    private String nome;

    @Column
    private String cnpj;
}
