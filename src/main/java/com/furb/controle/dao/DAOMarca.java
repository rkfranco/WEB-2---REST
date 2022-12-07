package com.furb.controle.dao;


import com.furb.controle.model.marca.MarcaDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DAOMarca extends CrudRepository<MarcaDAO, Integer> {

    Optional<MarcaDAO> findBycnpj(String cnpj);

    Optional<MarcaDAO> findBynome(String nome);

}
