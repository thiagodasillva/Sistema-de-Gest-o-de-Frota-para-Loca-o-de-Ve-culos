package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.controllers;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.AuthRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.AuthResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {

        String cpfSemFormatacao = authRequest.getCpf().replaceAll("[^0-9]", "");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(cpfSemFormatacao, authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(cpfSemFormatacao);
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }
}
