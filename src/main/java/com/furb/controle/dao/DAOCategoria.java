package com.furb.controle.dao;

import com.furb.controle.model.categoria.CategoriaDAO;
import com.furb.controle.model.produto.ProdutoDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DAOCategoria extends CrudRepository<CategoriaDAO, Integer> {

    Optional<CategoriaDAO> findByNome(String nome);

    Optional<CategoriaDAO> findByProduto(ProdutoDAO produto);

}