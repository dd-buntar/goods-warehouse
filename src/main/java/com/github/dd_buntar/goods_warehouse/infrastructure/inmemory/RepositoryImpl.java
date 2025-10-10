package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import java.util.HashMap;
import java.util.Map;

public class RepositoryImpl<T, ID> {  // ?????
    private final Map<ID, T> storage = new HashMap<>();
    private Long curId = 1L;

//    public T add(T entity, ID id) {
//        if (id == null) {
//            Long newId = curId++;
//            entity.setId(newId);
//        }
//    }

}
