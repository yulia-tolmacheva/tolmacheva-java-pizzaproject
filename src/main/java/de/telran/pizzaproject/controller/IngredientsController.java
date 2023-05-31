package de.telran.pizzaproject.controller;


import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.service.IngredientService;
import de.telran.pizzaproject.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientService service;

    public IngredientsController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Ingredient> ingredients = service.getAllIngredients();
//        model.addAttribute("ingredients", ingredients);
        model.addAttribute("ingredientToAdd", new Ingredient());
        return "ingredient/ingredients";
    }

    @PostMapping
    public String addIngredientsDetails(@ModelAttribute("ingredientToAdd") @Valid Ingredient ingredientToAdd,
                                  BindingResult result,
                                  RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredientToAdd", ingredientToAdd);
            return "/ingredient/ingredients";
        }
        Ingredient ingredient = service.addOrUpdate(ingredientToAdd);
        attributes.addFlashAttribute("added", ingredient.getId());
        return "redirect:/ingredients";
    }

    @PostMapping("/update")
    public String showUpdateFields(@RequestParam("ingredientToUpdateId") Long ingredientToUpdateId,
                                   Model model) {
        model.addAttribute("ingredientToUpdate", service.getIngredientById(ingredientToUpdateId));
        model.addAttribute("ingredientToUpdateId", ingredientToUpdateId);
        return "/ingredient/ingredients";
    }

    @PatchMapping("/update/{id}")
    public String updateIngredientsDetails(
            @ModelAttribute("ingredientToUpdate") @Valid Ingredient ingredientToUpdate,
                                     BindingResult result,
                                     Model model,
                                     @RequestParam("ingredientToUpdateId") Long ingredientToUpdateId,
                                     RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("ingredientToUpdateId", ingredientToUpdateId);
            return "/ingredient/ingredients";
        }
        Ingredient ingredient = service.addOrUpdate(ingredientToUpdate);
        attributes.addFlashAttribute("updated", ingredient.getId());
        return "redirect:/ingredients";
    }

    @DeleteMapping("/delete")
    public String deleteIngredient(@RequestParam("ingredientId") Long ingredientId,
                                     RedirectAttributes attributes) {
        service.deleteIngredient(ingredientId);
        attributes.addFlashAttribute("deleted", ingredientId);
        return "redirect:/ingredients";
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> getListIngredients() {
        return service.getAllIngredients();
    }

    @ModelAttribute("ingredientToUpdate")
    public Ingredient getIngredientToUpdate() {
        return new Ingredient();
    }
}
