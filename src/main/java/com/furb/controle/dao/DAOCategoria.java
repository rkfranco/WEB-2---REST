package com.furb.controle.dao;

import com.furb.controle.model.CategoriaDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DAOCategoria extends CrudRepository<CategoriaDAO, Integer> {

//    CategoriaDAO findByNome(String cnpj);

//    CategoriaDAO findByMarca(Integer id);
}