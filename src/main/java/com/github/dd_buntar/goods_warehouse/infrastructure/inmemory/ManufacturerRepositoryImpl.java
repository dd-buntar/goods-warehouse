package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ManufacturerRepositoryImpl implements ManufacturerRepository {
    private final Map<Long, Manufacturer> manufacturerStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Optional<Manufacturer> create(Manufacturer entity) {
        if (manufacturerStorage.containsKey(entity.getManufacturerId())) {
            return Optional.empty();
        }

        if (findByPhone(entity.getContactPhone()).isPresent()) {
            return Optional.empty();
        }

        Long nextId = idCounter.getAndIncrement();

        Manufacturer newManufacturer = Manufacturer.builder()
                .manufacturerId(nextId)
                .manufacturerName(entity.getManufacturerName())
                .contactPhone(entity.getContactPhone())
                .country(entity.getCountry())
                .build();

        manufacturerStorage.put(nextId, newManufacturer);
        return Optional.of(newManufacturer);
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
        if (manufacturerStorage.containsKey(entity.getManufacturerId())) {
            Long id = entity.getManufacturerId();
            Manufacturer curManufacturer = manufacturerStorage.get(id);

            if (entity.equals(curManufacturer)) {
                return Optional.of(curManufacturer);
            }

            Optional<Manufacturer> m = findByPhone(entity.getContactPhone());
            if (m.isPresent() && !m.get().getContactPhone().equals(curManufacturer.getContactPhone())) {
                return Optional.empty();
            }

            Manufacturer manufacturerToSave = Manufacturer.builder()
                    .manufacturerId(entity.getManufacturerId())
                    .manufacturerName(entity.getManufacturerName())
                    .contactPhone(entity.getContactPhone())
                    .country(entity.getCountry()).build();

            manufacturerStorage.put(manufacturerToSave.getManufacturerId(), manufacturerToSave);
            return Optional.of(manufacturerToSave);
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
    @Override
    public Optional<Manufacturer> findByName(String name) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getManufacturerName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Найти производителей по стране
     */
    @Override
    public List<Manufacturer> findByCountry(String country) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    /**
     * Найти производителей по телефону
     */
    @Override
    public Optional<Manufacturer> findByPhone(String phone) {
        return manufacturerStorage.values().stream()
                .filter(manufacturer -> manufacturer.getContactPhone().equals(phone))
                .findFirst();
    }

    /**
     * Обновить контактный телефон производителя
     */
    @Override
    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        if (findByPhone(newPhone).isPresent()) {
            return Optional.empty();
        }
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
