package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {
    @Autowired
    TransportRepository transportRepository;

    @Override
    public List<Transport> getAll() {
        return transportRepository.findAll();
    }

    @Override
    public Optional<Transport> get(Long id) {
        return transportRepository.findById(id);
    }

    @Override
    public boolean add(Transport transport) {
        try {
            transportRepository.save(transport);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
