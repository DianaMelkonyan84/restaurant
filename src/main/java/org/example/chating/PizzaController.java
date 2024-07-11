package org.example.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

@Controller

public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/pizzas")
    public String showPizzas(Model model){
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        model.addAttribute("pizzas", pizzas);
        return "pizzas";
    }

    @PostMapping("/addPizza")
    public String addPizza(@ModelAttribute("pizza") Pizza pizza,
                           @RequestParam("file") MultipartFile file
                           ) throws IOException {
        if (!file.isEmpty()) {
            // Save the uploaded file to the specified upload path
            byte[] bytes = file.getBytes();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);

            pizza.setPictureName(fileName);
        }

        pizzaService.savePizza(pizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/addPizza")
    public String showAddPizzaForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "add-pizza";
    }

}
