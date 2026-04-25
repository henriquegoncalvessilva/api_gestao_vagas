package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquegoncalves.gestao_vagas.exceptions.JobNotFoundException;
import br.com.henriquegoncalves.gestao_vagas.exceptions.UserNotFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        if (!candidateRepository.existsById(idCandidate)) {
            throw new UserNotFoundException();
        }

        if (!jobRepository.existsById(idJob)) {
            throw new JobNotFoundException();
        }

        ApplyJobEntity newJobApply = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        return this.applyJobRepository.save(newJobApply);

    }
}
