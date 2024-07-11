package org.example.chating;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public void savePizza(Pizza pizza) {
        pizzaRepository.save(pizza);
    }

    public Pizza getPizzaById(Long pizzaId) {
        return pizzaRepository.findById(pizzaId).get();
    }
}
