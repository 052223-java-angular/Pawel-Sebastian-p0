package com.revature.ecommerce_cli.DAO;
import java.util.Optional;

import java.util.List;

public interface CrudDAO<T> {
    void save(T obj);

    void update(T obj);

    void delete(String id);

    Optional<T> findById(String id);

    List<T> findAll();
}
