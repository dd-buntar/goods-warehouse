package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StorageLocationRepository extends Repository<StorageLocation, Long>{
    List<StorageLocation> findByRackNumber(Integer rackNum);
    Set<Integer> findAllRackNumbers();
    List<Integer> findShelvesByRack(Integer rackNum);
}
