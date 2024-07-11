package org.example.chating;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String userRegister(User user) {
        user.setRegisteredAt(LocalDateTime.now());
        String token=TokenGenerator.generateToken();
        user.setToken(token);
        userService.saveUser(user);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String registrationSuccess(Model model) {
        model.addAttribute("user", new User());
        return "success";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/error")
    public String showError(Model model) {
        model.addAttribute("user", new User());
        return "error";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user,
                            @RequestParam("identification_picture1") MultipartFile identificationPicture1,
                            @RequestParam("identification_picture2") MultipartFile identificationPicture2
    ) throws IOException {
        try {
            if (!Arrays.equals(identificationPicture1.getBytes(), identificationPicture2.getBytes())) {
                return "redirect:/error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        if (!identificationPicture1.isEmpty() && !identificationPicture2.isEmpty()) {
            // Save the uploaded file to the specified upload path
            byte[] bytes = identificationPicture1.getBytes();
            String fileName = System.currentTimeMillis() + "_" + identificationPicture1.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);

            user.setPictureName(fileName);

        }
        user.setAuthenticated(true);
        userService.saveUser(user);
        return "redirect:/successful";
    }

    @GetMapping("/successful")
    public String showSuccessful(Model model) {
        model.addAttribute("user", new User());
        return "successful";
    }


    @GetMapping("/message")
    public String showMessage(Model model) {
        model.addAttribute("user", new User());
        return "message";
    }

    @PostMapping("/message")
    public String sendMessageToAdmin(@RequestParam("userId") Long userId, @RequestParam("content") String content) {

        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        user.setRegisteredAt(LocalDateTime.now());

        user.setMessageToAdmin(content);
        userService.saveUser(user);

        // Redirect to a success page or another appropriate page
        return "redirect:/sms";
    }
    @GetMapping("/sms")
    public String showSms(Model model) {
        model.addAttribute("user", new User());
        return "sms";
    }
}
