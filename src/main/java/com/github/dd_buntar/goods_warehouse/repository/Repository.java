package com.github.dd_buntar.goods_warehouse.repository;

import java.util.Optional;
import java.util.List;

public interface Repository<T, ID> {
    T add(final T entity);
    Optional<T> getById(final ID id);
    List<T> getAll();
    Optional<T> update(final T entity);
    boolean deleteById(final ID id);
}
