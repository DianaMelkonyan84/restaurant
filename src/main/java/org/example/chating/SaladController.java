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
public class SaladController {
    @Autowired
    private SaladService saladService;
    @Value("${upload.path}")
    private String uploadPath;


     @GetMapping("/salad")
    public String showSalad(Model model){
        List<Salad> salads = saladService.getAllPizzas();
        model.addAttribute("salads", salads);
        return "salad";
    }


    @GetMapping("/addSalad")
    public String showAddSaladForm(Model model) {
        model.addAttribute("salad", new Salad());
        return "add-salad";
    }

    @PostMapping("/addSalad")
    public String addPizza(@ModelAttribute("salad") Salad salad,
                           @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // Save the uploaded file to the specified upload path
            byte[] bytes = file.getBytes();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);

            salad.setPictureName(fileName);

        }

        saladService.savePizza(salad);

        return "redirect:/salad";
    }

}
