package com.furb.controle.dao;

import com.furb.controle.dao.repository.CategoriaRepository;
import com.furb.controle.model.CategoriaDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DAOCategoria extends CrudRepository<CategoriaDAO, Integer>, CategoriaRepository<CategoriaDAO, Integer> {

}