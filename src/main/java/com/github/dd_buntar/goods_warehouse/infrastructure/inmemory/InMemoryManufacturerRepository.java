package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryManufacturerRepository implements ManufacturerRepository {
    private final Map<Long, Manufacturer> manufacturerStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Manufacturer create(Manufacturer entity) {
        Long nextId = idCounter.getAndIncrement();

        Manufacturer newManufacturer = Manufacturer.builder()
                .manufacturerId(nextId)
                .manufacturerName(entity.getManufacturerName())
                .contactPhone(entity.getContactPhone())
                .country(entity.getCountry())
                .build();

        manufacturerStorage.put(nextId, newManufacturer);
        return newManufacturer;
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return Optional.ofNullable(manufacturerStorage.get(id));
    }

    @Override
    public List<Manufacturer> findAll() {
        return new ArrayList<>(manufacturerStorage.values());
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer entity) {
        if (entity.getManufacturerId() == null) {
            return Optional.empty();
        }

        if (manufacturerStorage.containsKey(entity.getManufacturerId())) {
            manufacturerStorage.put(entity.getManufacturerId(), entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return manufacturerStorage.remove(id) != null;
    }


    /**
     * Найти производителя по названию (точное совпадение)
     */
    public Optional<Manufacturer> findByName(String name) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getManufacturerName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Найти производителей по стране
     */
    public List<Manufacturer> findByCountry(String country) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    /**
     * Проверить существование производителя с таким названием
     */
    public boolean existsByName(String name) {
        return manufacturerStorage.values().stream()
                .anyMatch(manufacturer -> manufacturer.getManufacturerName().equalsIgnoreCase(name));
    }

    /**
     * Найти производителей по телефону
     */
    public Optional<Manufacturer> findByPhone(String phone) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getContactPhone().equals(phone))
                .findFirst();
    }

    /**
     * Обновить контактный телефон производителя
     */
    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        return findById(manufacturerId)
                .map(manufacturer -> {
                    Manufacturer updated = Manufacturer.builder()
                            .manufacturerId(manufacturer.getManufacturerId())
                            .manufacturerName(manufacturer.getManufacturerName())
                            .contactPhone(newPhone)
                            .country(manufacturer.getCountry())
                            .build();
                    manufacturerStorage.put(manufacturerId, updated);
                    return updated;
                });
    }
}
