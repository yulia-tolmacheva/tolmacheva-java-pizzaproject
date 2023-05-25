package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService service;

    public PizzaController(PizzaService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Pizza> pizzas = service.getAllPizzas();
        model.addAttribute("pizzas", pizzas);
        return "pizza/pizzas";
    }

    @GetMapping("/new")
    public String addPizzaDetails(@ModelAttribute("pizzaToAdd") Pizza pizza) {
        return "pizza/new";
    }

    @PostMapping("/new")
    public String addPizza(@ModelAttribute("pizzaToAdd") Pizza pizza, BindingResult result,
                           RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "pizza/new";
        }

        Pizza pizzaAdded = service.addOrUpdate(pizza);
        attributes.addFlashAttribute("added", pizzaAdded.getId());
        return "redirect:/pizzas";
    }

    @DeleteMapping("/delete")
    public String deletePizza(@RequestParam Long pizzaId, RedirectAttributes attributes) {
        service.deletePizza(pizzaId);
        attributes.addFlashAttribute("deleted", pizzaId);
        return "redirect:/pizzas";
    }

    @PostMapping("/edit")
    public String showFieldsEditPizza(@ModelAttribute("pizzaToUpdate") Pizza pizzaToUpdate,
            @RequestParam Long pizzaToUpdateId,
                                      HttpServletRequest request, RedirectAttributes attributes, Model model) {
//        Long pizzaToUpdateId = Long.getLong(request.getParameter("pizzaToUpdateId"));
        Pizza pizza = service.getPizzaById(pizzaToUpdateId);
        System.out.println("pizza = " + pizza.getId());
        model.addAttribute("pizzaToUpdate", pizza);
        attributes.addFlashAttribute("pizzaToUpdateId", pizzaToUpdateId);

//        attributes.addFlashAttribute("pizzaToUpdateId", pizzaToUpdateId);
//        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//        if (flashMap != null) {
//            Long pizzaId = (Long) flashMap.get("pizzaToUpdateId");
//            Pizza pizza = service.getPizzaById(pizzaId);
//            if (pizza != null) {
//                model.addAttribute("pizzaToUpdate", pizza);
//            }
//        }
        return "redirect:/pizzas";
    }

    @PostMapping("/edit/{id}")
    public String editPizza(@ModelAttribute("pizzaToUpdate") Pizza pizzaToUpdate,
                            HttpServletRequest request,
                            Model model, RedirectAttributes attributes) {
//        Pizza pizza1 = (Pizza) model.getAttribute("pizzaToUpdate");
//        Pizza updated = service.addOrUpdate(pizza1);
//        attributes.addFlashAttribute("updated", updated.getId());
        service.addOrUpdate(pizzaToUpdate);
        return "redirect:/pizzas";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("pizzaToUpdate", new Pizza(0L));
    }
}
