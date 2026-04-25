package br.com.henriquegoncalves.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "Desenvolvedor Java")
    private String description;
    @Schema(example = "example@email.com")
    private String email;
    @Schema(example = "biro")
    private String username;
    private String id;
    @Schema(example = "Henrique gonçalves silva")
    private String name;
}
