package com.furb.controle.dao;

import com.furb.controle.dao.repository.ProdutoRepository;
import com.furb.controle.model.ProdutoDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOProduto extends CrudRepository<ProdutoDAO, Integer>, ProdutoRepository<ProdutoDAO, Integer> {

}
