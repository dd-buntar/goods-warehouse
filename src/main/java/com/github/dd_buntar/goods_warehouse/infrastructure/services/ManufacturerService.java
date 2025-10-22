package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;
import lombok.NonNull;

import java.util.*;

public class ManufacturerService {
    private static final Set<String> COUNTRIES = new HashSet<>(Arrays.asList("Россия", "Беларусь"));
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Optional<Manufacturer> create(final Manufacturer manufacturer) {
        validateManufacturerName(manufacturer.getManufacturerName());
        validatePhoneFormat(manufacturer.getContactPhone(), manufacturer.getCountry());
        validateCountry(manufacturer.getCountry());
        Optional<Manufacturer> curManufacturer = manufacturerRepository.create(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером уже существует");
        }
        return curManufacturer;
    }

    public Optional<Manufacturer> findById(@NonNull final Long id) {
        return manufacturerRepository.findById(id);
    }

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> update(final Manufacturer manufacturer) {
        validateManufacturer(manufacturer);
        Optional<Manufacturer> curManufacturer = manufacturerRepository.update(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером телефона уже существует или id нет в хранилище");
        }
        return curManufacturer;
    }

    public boolean deleteById(@NonNull final Long id) {
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
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (!manufacturer.isPresent()) {
            throw new IllegalArgumentException("Производителя с таким id не существует");
        }

        validateManufacturerId(manufacturerId);
        validatePhoneFormat(newPhone, manufacturer.get().getCountry());
        Optional<Manufacturer> curManufacturer = manufacturerRepository.updatePhone(manufacturerId, newPhone);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером телефона уже существует");
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

    private void validatePhoneFormat(String contactPhone, String country) {
        if (contactPhone == null || contactPhone.isEmpty()) {
            throw new IllegalArgumentException("Телефон производителя не должен быть пустой строкой");
        }
        if (!contactPhone.matches("^\\+?[0-9\\s\\-]+$")) {
            throw new IllegalArgumentException("Номер телефона содержит недопустимые символы");
        }
        switch (country.toLowerCase()) {
            case "россия":
                if (!contactPhone.matches("^\\+7[0-9]{10}$")) {
                    throw new IllegalArgumentException(
                            "Для России номер телефона должен быть в формате +7XXXXXXXXXX");
                }
                break;
            case "беларусь":
                if (!contactPhone.matches("^\\+375[0-9]{9}$")) {
                    throw new IllegalArgumentException(
                            "Для Беларуси номер телефона должен быть в формате +375XXXXXXXXX");
                }
                break;
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Страна производителя не должна быть пустой строкой");
        }
        if (country.length() > 50) {
            throw new IllegalArgumentException("Название страны не может превышать 50 символов");
        }
        if (!country.matches("^[а-яА-ЯёЁ\\s-]+$")) {
            throw new IllegalArgumentException("Название страны содержит недопустимые символы");
        }

        if (!COUNTRIES.contains(country)) {
            throw new IllegalArgumentException(
                    "Страна производителя не входит в список поставщиков:" +
                            "\n 1. " + COUNTRIES.toArray()[1] + " \n 2. " + COUNTRIES.toArray()[2]);
        }
    }

    private void validateManufacturer(Manufacturer manufacturer) {
        validateManufacturerId(manufacturer.getManufacturerId());
        validateManufacturerName(manufacturer.getManufacturerName());
        validatePhoneFormat(manufacturer.getContactPhone(), manufacturer.getCountry());
        validateCountry(manufacturer.getCountry());
    }
}
