package br.com.henriquegoncalves.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static String objectToJson(Object obj) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID idCompany) {
        Algorithm algorithm = Algorithm.HMAC256("JAVAGAS_@123#$");
        String token = JWT.create().withIssuer("javagas").withSubject(idCompany.toString())
                .withClaim("roles", Arrays.asList("COMPANY")).withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .sign(algorithm);

        return token;
    }
}
