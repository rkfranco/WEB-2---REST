package com.furb.controle.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Embeddable
public class ProdutosCadastradosPK {
    @ManyToMany
    @JoinColumn(name="produto_marca_id", referencedColumnName="MARCA_ID")
    private MarcaDAO marca;

    @ManyToMany
    @JoinColumn(name="produto_categoria_id", referencedColumnName="CATEGORIA_ID")
    private CategoriaDAO categoria;

    // getters e setters
}
