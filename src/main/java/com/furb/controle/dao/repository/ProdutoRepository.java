package com.furb.controle.dao.repository;

import com.furb.controle.model.MarcaDAO;
import com.furb.controle.model.ProdutoDAO;

import java.util.Optional;

public interface ProdutoRepository<T, S> {

      Optional<ProdutoDAO> findBynome(String nome);

      Optional<ProdutoDAO[]> findByMarca(MarcaDAO marca);

      Optional<ProdutoDAO[]> findByCategoriaId(Integer id);
      // TODO: REVER ISSO
}

//    ProdutoDAO findByCNPJ(String cnpj);
//    ProdutoDAO[] findAbovePrice(Double price);
//    ProdutoDAO[] findBellowPrice(Double price);