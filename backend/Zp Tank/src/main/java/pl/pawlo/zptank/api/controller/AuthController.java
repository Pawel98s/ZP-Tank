package pl.pawlo.zptank.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawlo.zptank.api.dto.employee.LoginRequestDTO;
import pl.pawlo.zptank.api.dto.employee.LoginResponseDTO;
import pl.pawlo.zptank.security.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request) {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword());

        authenticationManager.authenticate(authentication);

        UserDetails user =
                userDetailsService.loadUserByUsername(
                        request.getLogin());

        String token = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(
                token,
                null,
                "Bearer",
                900
        );
    }
}
