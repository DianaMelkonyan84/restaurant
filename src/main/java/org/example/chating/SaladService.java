package org.example.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaladService {
    @Autowired
     private SaladRepository saladRepository;

    public List<Salad> getAllPizzas() {
        return saladRepository.findAll();
    }

    public void savePizza(Salad salad) {
        saladRepository.save(salad);
    }

    public Salad getSaladById(Long saladId) {
        return saladRepository.findById(saladId).get();
    }
}
