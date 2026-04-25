package br.com.henriquegoncalves.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJobDTO {
    @Schema(example = "Vaga para pessoa desenvolvedora sênior", requiredMode = RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Plano de saúde, etc", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "Sênior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
