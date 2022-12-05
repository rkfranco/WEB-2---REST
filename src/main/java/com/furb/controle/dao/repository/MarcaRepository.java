package com.furb.controle.dao.repository;

import com.furb.controle.model.marca.MarcaDAO;

import java.util.Optional;

public interface MarcaRepository<T, S> {
    Optional<MarcaDAO> findBycnpj(String cnpj);

    Optional<MarcaDAO> findBynome(String nome);
}
