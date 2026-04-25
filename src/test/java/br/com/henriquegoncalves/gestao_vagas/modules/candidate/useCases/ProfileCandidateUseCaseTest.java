package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.henriquegoncalves.gestao_vagas.exceptions.UserNotFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {
    @InjectMocks
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    public void should_be_able_return_candidate_profile() {

        UUID id = UUID.randomUUID();

        CandidateEntity entity = CandidateEntity.builder()
                .id(id)
                .name("Henrique")
                .email("email@email.com")
                .username("usernameaqui")
                .description("Descrição")
                .build();

        when(candidateRepository.findById(id)).thenReturn(Optional.of(entity));

        ProfileCandidateResponseDTO resultDTO = profileCandidateUseCase.execute(id);

        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getName()).isEqualTo("Henrique");
        assertThat(resultDTO.getEmail()).isEqualTo("email@email.com");

    };

    @Test
    public void should_not_be_able_return_candidate_profile_if_idCandidate_not_found() {

        UUID id = UUID.randomUUID();

        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profileCandidateUseCase.execute(id))
                .isInstanceOf(UserNotFoundException.class);

    };
}
