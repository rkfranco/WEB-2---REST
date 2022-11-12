package com.furb.controle.dao;

import com.furb.controle.model.MarcaDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class DAOMarcaImpl implements DAOMarca {
    @Override
    public MarcaDAO findByid(long id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("estoque");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MarcaDAO marca = entityManager.find(MarcaDAO.class, id);
        entityManager.close();
        entityManagerFactory.close();
        return marca;
    }

    @Override
    public MarcaDAO findByCNPJ(String cnpj) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("estoque");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MarcaDAO marca = entityManager.find(MarcaDAO.class, cnpj);
        entityManager.close();
        entityManagerFactory.close();
        return marca;
    }

    @Override
    public <S extends MarcaDAO> S save(S entity) {
        return null;
    }

    @Override
    public <S extends MarcaDAO> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<MarcaDAO> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("estoque");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MarcaDAO marca = entityManager.find(MarcaDAO.class, integer);
        entityManager.close();
        entityManagerFactory.close();
        return marca != null;
    }

    @Override
    public Iterable<MarcaDAO> findAll() {
        return null;
    }

    @Override
    public Iterable<MarcaDAO> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(MarcaDAO entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends MarcaDAO> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
