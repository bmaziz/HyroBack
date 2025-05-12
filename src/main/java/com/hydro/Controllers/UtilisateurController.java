package com.hydro.Controllers;

import com.hydro.Entities.Utilisateur;
import com.hydro.Services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAllUsers() {
        return utilisateurService.getAllUsers();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Utilisateur> getUserByLogin(@PathVariable String login) {
        return utilisateurService.getUserByLogin(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{login}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable String login, @RequestBody Utilisateur updatedUser) {
        return utilisateurService.updateUser(login, updatedUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{login}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String login, @RequestBody String newPassword) {
        return utilisateurService.updatePassword(login, newPassword)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        if (utilisateurService.deleteUser(login)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
