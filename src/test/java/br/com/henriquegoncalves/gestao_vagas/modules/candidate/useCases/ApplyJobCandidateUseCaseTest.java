package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.henriquegoncalves.gestao_vagas.exceptions.JobNotFoundException;
import br.com.henriquegoncalves.gestao_vagas.exceptions.UserNotFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;
    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able attach candidate to job with invalid parameters")
    void should_not_be_able_attach_candidate_to_job_with_invalid_parameters() {

        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }

    }

    @Test
    @DisplayName("Should not be able to apply a job with job not found")
    void should_not_be_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();

        candidate.setId(idCandidate);

        when(candidateRepository.existsById(idCandidate))
                .thenReturn(true); //

        when(jobRepository.existsById(null))
                .thenReturn(false); //
        assertThatThrownBy(() -> applyJobCandidateUseCase.execute(idCandidate, null))
                .isInstanceOf(JobNotFoundException.class);
    }

    @Test
    void should_be_able_to_create_a_new_apply_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        when(candidateRepository.existsById(idCandidate))
                .thenReturn(true);

        when(jobRepository.existsById(idJob))
                .thenReturn(true);

        when(applyJobRepository.save(any(ApplyJobEntity.class)))
                .thenReturn(
                        ApplyJobEntity.builder()
                                .id(UUID.randomUUID())
                                .candidateId(idCandidate)
                                .jobId(idJob).build());

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertNotNull(result);
        assertNotNull(result.getId());

    }

}
