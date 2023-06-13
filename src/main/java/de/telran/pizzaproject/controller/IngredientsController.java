package de.telran.pizzaproject.controller;


import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.service.IngredientService;
import de.telran.pizzaproject.validator.IngredientValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientService service;
    private final IngredientValidator validator;

    public IngredientsController(IngredientService service, IngredientValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("ingredientToAdd", new Ingredient());
        return "ingredient/ingredients";
    }

    @PostMapping
    public String addIngredientsDetails(@ModelAttribute("ingredientToAdd") @Valid Ingredient ingredientToAdd,
                                        BindingResult result,
                                        RedirectAttributes attributes, Model model) {
        validator.validate(ingredientToAdd, result);
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
            RedirectAttributes attributes) {
        validator.validate(ingredientToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("ingredientToUpdateId", ingredientToUpdate.getId());
            return "/ingredient/ingredients";
        }
        Ingredient ingredient = service.addOrUpdate(ingredientToUpdate);
        attributes.addFlashAttribute("updated", ingredient.getId());
        return "redirect:/ingredients";
    }

    @DeleteMapping("/delete")
    public String deleteIngredient(@RequestParam("ingredientId") Long ingredientId,
                                   RedirectAttributes attributes) {
        if (service.getIngredientById(ingredientId).getPizzas().size() > 0) {
            attributes.addFlashAttribute("ingredientBlocked", ingredientId);
            return "redirect:/ingredients";
        }
        service.deleteIngredient(ingredientId);
        attributes.addFlashAttribute("deleted", ingredientId);
        return "redirect:/ingredients";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("ingredients", service.getAllIngredients());
    }
}
