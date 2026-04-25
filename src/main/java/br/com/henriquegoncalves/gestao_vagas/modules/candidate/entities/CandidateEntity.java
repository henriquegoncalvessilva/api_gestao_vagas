package br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "candidate")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Schema(example = "Luiz Henrique", requiredMode = RequiredMode.REQUIRED)
    private String name;
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    @Schema(example = "henriquegoncalvessilva", requiredMode = RequiredMode.REQUIRED)
    private String username;
    @Email(message = "O campo [email] deve conter um e-mail válido")
    @Schema(example = "exemplo@email.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "\\S+", message = "O campo nao pode conter espaco em branco")
    @Length(min = 10, max = 100)
    @Schema(example = "12345678910", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, nullable = false)
    @NotNull
    @NotBlank
    private String password;
    @Schema(example = "Desenvolvedor FullStack")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
