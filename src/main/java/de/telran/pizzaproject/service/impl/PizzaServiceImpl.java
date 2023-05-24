package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.PizzaRepository;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository repository;

    public PizzaServiceImpl(PizzaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return repository.findAll();
    }
}
