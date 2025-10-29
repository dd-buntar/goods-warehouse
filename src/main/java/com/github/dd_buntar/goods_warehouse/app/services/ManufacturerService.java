package com.github.dd_buntar.goods_warehouse.app.services;

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

    public Manufacturer create(@NonNull final Manufacturer manufacturer) {
        validateManufacturerName(manufacturer.getManufacturerName());
        validatePhoneFormat(manufacturer.getContactPhone(), manufacturer.getCountry());
        validateCountry(manufacturer.getCountry());
        Optional<Manufacturer> curManufacturer = manufacturerRepository.create(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Производитель с таким номером уже существует");
        }
        return curManufacturer.get();
    }

    public Manufacturer findById(@NonNull final Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (!manufacturer.isPresent()) {
            throw new IllegalArgumentException("Производителя с таким id не существует");
        }
        return manufacturer.get();
    }

    public List<Manufacturer> findAll() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        if (manufacturers.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return manufacturers;
    }

    public Manufacturer update(@NonNull final Manufacturer manufacturer) {
        validateManufacturer(manufacturer);
        Optional<Manufacturer> curManufacturer = manufacturerRepository.update(manufacturer);
        if (!curManufacturer.isPresent()) {
            throw new IllegalArgumentException("Такого id нет в хранилище");
        }
        if (!curManufacturer.get().equals(manufacturer)) {
            throw new IllegalArgumentException("Производитель с таким номером телефона уже существует");
        }
        return curManufacturer.get();
    }

    public boolean deleteById(@NonNull final Long id) {
        boolean isDeleted = manufacturerRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Производителя с таким id не существует");
        }
        return true;
    }

    public Manufacturer findByName(@NonNull final String name) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findByName(name);
        if (!manufacturer.isPresent()) {
            throw new IllegalArgumentException("Производителя с таким именем не существует");
        }
        return manufacturer.get();
    }

    public List<Manufacturer> findByCountry(@NonNull final String country) {
        List<Manufacturer> manufacturers = manufacturerRepository.findByCountry(country);
        if (manufacturers.isEmpty()) {
            throw new IllegalArgumentException("Производителей с такой страной не существует");
        }
        return manufacturers;
    }

    public Manufacturer findByPhone(@NonNull final String phone) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findByPhone(phone);
        if (!manufacturer.isPresent()) {
            throw new IllegalArgumentException("Производителя с таким телефоном не существует");
        }
        return manufacturer.get();
    }

    public Manufacturer updatePhone(@NonNull final Long manufacturerId, @NonNull final String newPhone) {
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
        return curManufacturer.get();
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
                            "\n 1. " + COUNTRIES.toArray()[0] + " \n 2. " + COUNTRIES.toArray()[1]);
        }
    }

    private void validateManufacturer(Manufacturer manufacturer) {
        validateManufacturerId(manufacturer.getManufacturerId());
        validateManufacturerName(manufacturer.getManufacturerName());
        validatePhoneFormat(manufacturer.getContactPhone(), manufacturer.getCountry());
        validateCountry(manufacturer.getCountry());
    }
}
