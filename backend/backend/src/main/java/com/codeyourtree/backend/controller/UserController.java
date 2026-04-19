package com.codeyourtree.backend.controller;

import com.codeyourtree.backend.model.User;
import com.codeyourtree.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Bu annot sınıfın bir api olduğunu ve json döneceğini belirtir.
@RestController
@RequestMapping("/api/users") // ana adresimiz localhost.../api/users olacak
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // frontendin farklı portlardaki sitelerin erişimine izin verir.
public class UserController {
    private final UserService userService;

    /*
     * Data Transfer Object
     * Frontendden gelen json verisini eşleştireceğimiz kısım.
     * Dışarıdan gelen her veriyi veritabanına yani user'a koymak güvenlik açığı
     * oluşturabilir.
     * Bu yüzden araya böyle basit bir dto koyuyoruz.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }

    /*
     * Bu kısım da frontendin POST request atacağı yer. yani endpoint.
     */

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            User savedUser = userService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword());
            return ResponseEntity.ok("Registration is successful. Welcome " + savedUser.getUsername() + " !");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegisterRequest request) {
        try {
            User loggedUser = userService.loginUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(loggedUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
