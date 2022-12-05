package com.furb.controle.dao.repository;

import com.furb.controle.model.categoria.CategoriaDAO;
import com.furb.controle.model.marca.MarcaDAO;
import com.furb.controle.model.produto.ProdutoDAO;

import java.util.Optional;

public interface ProdutoRepository<T, S> {

      Optional<ProdutoDAO> findBynome(String nome);

      Optional<ProdutoDAO[]> findByMarca(MarcaDAO marca);

      Optional<ProdutoDAO[]> findByCategoria(CategoriaDAO produto);

}

//    ProdutoDAO findByCNPJ(String cnpj);
//    ProdutoDAO[] findAbovePrice(Double price);
//    ProdutoDAO[] findBellowPrice(Double price);