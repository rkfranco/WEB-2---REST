package com.furb.controle.dao;

import com.furb.controle.model.categoria.DAOCategoria;
import com.furb.controle.model.produto.DAOProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoriaDAO extends CrudRepository<DAOCategoria, Integer> {

    Optional<DAOCategoria> findByNome(String nome);

    Optional<DAOCategoria> findByProduto(DAOProduto produto);

}