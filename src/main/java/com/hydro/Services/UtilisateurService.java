package com.hydro.Services;

import com.hydro.Entities.Utilisateur;
import com.hydro.Repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUserByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    public Optional<Utilisateur> updateUser(String login, Utilisateur updatedUser) {
        return utilisateurRepository.findByLogin(login).map(user -> {
            user.setAdresse(updatedUser.getAdresse());
            user.setInstitution(updatedUser.getInstitution());
            user.setTypeInst(updatedUser.getTypeInst());
            user.setEmail(updatedUser.getEmail());
            return utilisateurRepository.save(user);
        });
    }

    public Optional<String> updatePassword(String login, String newPassword) {
        return utilisateurRepository.findByLogin(login).map(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            utilisateurRepository.save(user);
            return "Password updated successfully";
        });
    }

    public boolean deleteUser(String login) {
        return utilisateurRepository.findByLogin(login).map(user -> {
            utilisateurRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
