package com.furb.controle.dao;


import com.furb.controle.model.marca.DAOMarca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaDAO extends CrudRepository<DAOMarca, Integer> {

    Optional<DAOMarca> findBycnpj(String cnpj);

    Optional<DAOMarca> findBynome(String nome);

}
