package com.monitoring.audit_log_crud.controller;
import com.monitoring.audit_log_crud.dto.AuthDTO;
import com.monitoring.audit_log_crud.dto.TokenDTO;
import com.monitoring.audit_log_crud.model.User;
import com.monitoring.audit_log_crud.repository.UserRepository;
import com.monitoring.audit_log_crud.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager manager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager manager, UserRepository repository, TokenService tokenService, PasswordEncoder encoder) {
        this.manager = manager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO data) {
        var tokenAuth = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = manager.authenticate(tokenAuth);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User data) {
        if(repository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();
        data.setPassword(encoder.encode(data.getPassword()));
        repository.save(data);
        return ResponseEntity.ok().build();
    }
}
