package com.furb.controle.dao.repository;

import com.furb.controle.model.categoria.CategoriaDAO;
import com.furb.controle.model.produto.ProdutoDAO;

import java.util.Optional;

public interface CategoriaRepository<T, S> {
    Optional<CategoriaDAO> findByNome(String nome);

    Optional<CategoriaDAO> findByProduto(ProdutoDAO produto);
}
