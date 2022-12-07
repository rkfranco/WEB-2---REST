package com.furb.controle.dao;

import com.furb.controle.model.categoria.CategoriaDAO;
import com.furb.controle.model.marca.MarcaDAO;
import com.furb.controle.model.produto.ProdutoDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DAOProduto extends CrudRepository<ProdutoDAO, Integer> {

    Optional<ProdutoDAO> findBynome(String nome);

    Optional<ProdutoDAO[]> findByMarca(MarcaDAO marca);

    Optional<ProdutoDAO[]> findByCategoria(CategoriaDAO produto);

}
