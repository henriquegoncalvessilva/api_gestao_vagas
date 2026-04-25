package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.henriquegoncalves.gestao_vagas.exceptions.UserFieldsNotCorrectException;
import br.com.henriquegoncalves.gestao_vagas.exceptions.UserFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {

        if (candidateEntity.getEmail() == null) {
            throw new UserFieldsNotCorrectException();
        }
        if (candidateEntity.getUsername() == null) {
            throw new UserFieldsNotCorrectException();
        }

        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });
        
                String passwordCrypto = passwordEncoder.encode(candidateEntity.getPassword());

                candidateEntity.setPassword(passwordCrypto);

        return this.candidateRepository.save(candidateEntity);
    }
}
