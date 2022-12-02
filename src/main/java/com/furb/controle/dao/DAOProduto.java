package com.furb.controle.dao;

import com.furb.controle.model.ProdutoDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOProduto extends CrudRepository<ProdutoDAO, Integer> {
//    ProdutoDAO findByNome(String nome);
//
//    ProdutoDAO findByCNPJ(String cnpj);
//
//    ProdutoDAO[] findByMarca(long id);
//
//    ProdutoDAO[] findByCategoria(long id);
//
//    ProdutoDAO[] findAbovePrice(Double price);
//
//    ProdutoDAO[] findBellowPrice(Double price);
}
