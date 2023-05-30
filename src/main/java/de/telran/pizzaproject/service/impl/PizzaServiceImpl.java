package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.PizzaRepository;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Pizza getPizzaById(Long pizzaId) {
        return repository.getReferenceById(pizzaId);
    }
    @Override
    public Pizza addOrUpdate(Pizza pizza) {
        return repository.save(pizza);
    }

    @Override
    public void deletePizza(Long pizzaId) {
        repository.deleteById(pizzaId);
    }

}
