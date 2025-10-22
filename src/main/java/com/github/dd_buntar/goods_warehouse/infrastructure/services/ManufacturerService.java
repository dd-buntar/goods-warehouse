package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    Optional<Manufacturer> create(final Manufacturer manufacturer) {
        validateManufacturerName(manufacturer.getManufacturerName());
        validateContactPhone(manufacturer.getContactPhone());
        validateCountry(manufacturer.getCountry());
        Optional<Manufacturer> curManufacturer = manufacturerRepository.create(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером уже существует");
        }
        return curManufacturer;
    }

    Optional<Manufacturer> findById(@NonNull final Long id) {
        return manufacturerRepository.findById(id);
    }

    List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    Optional<Manufacturer> update(final Manufacturer manufacturer) {
        validateManufacturer(manufacturer);
        Optional<Manufacturer> curManufacturer = manufacturerRepository.update(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером телефона уже существует или id нет в хранилище");
        }
        return curManufacturer;
    }

    boolean deleteById(@NonNull final Long id) {
        return manufacturerRepository.deleteById(id);
    }

    public Optional<Manufacturer> findByName(@NonNull String name) {
        return manufacturerRepository.findByName(name);
    }

    public List<Manufacturer> findByCountry(@NonNull String country) {
        return manufacturerRepository.findByCountry(country);
    }

    public Optional<Manufacturer> findByPhone(@NonNull String phone) {
        return manufacturerRepository.findByPhone(phone);
    }

    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        validateManufacturerId(manufacturerId);
        validateContactPhone(newPhone);
        Optional<Manufacturer> curManufacturer = manufacturerRepository.updatePhone(manufacturerId, newPhone);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером телефона уже существует или id нет в хранилище");
        }
        return curManufacturer;
    }

    private void validateManufacturerId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID производителя должен быть положительным числом");
        }
    }

    private void validateManufacturerName(String manufacturerName) {
        if (manufacturerName == null || manufacturerName.isEmpty()) {
            throw new IllegalArgumentException("Имя производителя не должно быть пустой строкой");
        }
    }

    private void validateContactPhone(String contactPhone) {
        if (contactPhone == null || contactPhone.isEmpty()) {
            throw new IllegalArgumentException("Телефон производителя не должен быть пустой строкой");
        }

        if (!contactPhone.matches("\\d+")) {
            throw new IllegalArgumentException("Телефон должен содержать только цифры");
        }

        if (contactPhone.length() != 11) {
            throw new IllegalArgumentException("Телефон должен состоять из 11 цифр");
        }

        if (contactPhone.charAt(0) != '8') {
            throw new IllegalArgumentException("Телефон должен начинаться с цифры 8");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Страна производителя не должна быть пустой строкой");
        }
    }

    private void validateManufacturer(Manufacturer manufacturer) {
        validateManufacturerId(manufacturer.getManufacturerId());
        validateManufacturerName(manufacturer.getManufacturerName());
        validateContactPhone(manufacturer.getContactPhone());
        validateCountry(manufacturer.getCountry());
    }
}
