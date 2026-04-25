package br.com.henriquegoncalves.gestao_vagas.modules.company.useCases;

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

import br.com.henriquegoncalves.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Username/Password incorrect");
                });

        if (!checkPassword(authCompanyDTO.getPassword(), company.getPassword())) {
            
            throw new AuthenticationException();
        }

        return generateAuthToken(company.getId().toString());

    }

    private Boolean checkPassword(String authCompanyDTOPassword, String companyPassword) {
        boolean passwordMatches = this.passwordEncoder.matches(authCompanyDTOPassword, companyPassword);
        return passwordMatches;
    }

    private AuthCompanyResponseDTO generateAuthToken(String idCompany) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create().withIssuer("javagas").withSubject(idCompany)
                .withClaim("roles", Arrays.asList("COMPANY")).withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder().access_token(token)
                .expries_in(Instant.now().plus(Duration.ofHours(2)).toEpochMilli()).build();

        return authCompanyResponseDTO;
    }
}
