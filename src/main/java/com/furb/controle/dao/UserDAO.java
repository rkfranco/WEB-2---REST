package com.furb.controle.dao;

import com.furb.controle.model.user.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}
