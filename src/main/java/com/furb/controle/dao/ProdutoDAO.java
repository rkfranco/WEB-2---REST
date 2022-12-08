package com.furb.controle.dao;

import com.furb.controle.model.categoria.DAOCategoria;
import com.furb.controle.model.marca.DAOMarca;
import com.furb.controle.model.produto.DAOProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoDAO extends CrudRepository<DAOProduto, Integer> {

    Optional<DAOProduto> findBynome(String nome);

    Optional<DAOProduto[]> findByMarca(DAOMarca marca);

    Optional<DAOProduto[]> findByCategoria(DAOCategoria produto);

}
