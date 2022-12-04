package com.furb.controle.dao.repository;

import com.furb.controle.model.CategoriaDAO;
import com.furb.controle.model.MarcaDAO;

import java.util.Optional;

public interface CategoriaRepository<T, S> {
    Optional<CategoriaDAO> findByNome(String nome);

    Optional<CategoriaDAO[]> findByMarca(MarcaDAO marca);
}
