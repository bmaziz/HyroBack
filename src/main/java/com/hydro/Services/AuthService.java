package com.hydro.Services;

import com.hydro.dto.*;
import com.hydro.Entities.*;
import com.hydro.Repositories.UtilisateurRepository;
import com.hydro.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (utilisateurRepository.existsByLogin(request.getLogin()) || utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Login or Email already exists");
        }
        Utilisateur user = new Utilisateur();
        user.setLogin(request.getLogin());
        user.setEmail(request.getEmail());
        user.setAdresse(request.getAdresse());
        user.setInstitution(request.getInstitution());
        user.setTypeInst(request.getTypeInst());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.UTILISATEUR);
        utilisateurRepository.save(user);
    }

    public String authenticate(LoginRequest request) {
        Utilisateur user = utilisateurRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getLogin());
    }
}
