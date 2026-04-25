package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.henriquegoncalves.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
            throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Bad credentials");
                });

        if (!checkPassword(authCandidateRequestDTO.password(), candidate.getPassword())) {
            throw new AuthenticationException();

        }

         Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        String token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);


        AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
        return authCandidateResponse;

    }

    private Boolean checkPassword(String authCandidateDTOPassword, String candidatePassword) {
        boolean passwordMatches = this.passwordEncoder.matches(authCandidateDTOPassword, candidatePassword);
        return passwordMatches;
    }

}
