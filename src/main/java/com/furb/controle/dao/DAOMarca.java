package com.furb.controle.dao;


import com.furb.controle.model.MarcaDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOMarca  extends CrudRepository<MarcaDAO, Integer> {

    MarcaDAO findByid(long id);

    MarcaDAO findByCNPJ(String cnpj);
}
