package com.github.dd_buntar.goods_warehouse.domain.repositories;

import java.util.Optional;
import java.util.List;

public interface Repository<T, ID> {
    Optional<T> create(final T entity);

    Optional<T> findById(final ID id);

    List<T> findAll();

    Optional<T> update(final T entity);

    boolean deleteById(final ID id);
}
