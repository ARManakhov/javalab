package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public Optional<City> get(Long id) {
        return cityRepository.findById(id);

    }
    @Override
    public City get(String name) {

        return cityRepository.findByName(name);

    }

    @Override
    public List<City> getAll(){
        return cityRepository.findAll();
    }

    @Override
    public boolean add(City City) {
        try {
            cityRepository.save(City);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
