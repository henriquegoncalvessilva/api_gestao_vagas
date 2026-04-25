package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.henriquegoncalves.gestao_vagas.modules.company.entities.JobEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
class ListAllJobByFilterUseCaseTest {

        @InjectMocks
        private ListAllJobByFilterUseCase listAllJobByFilterUseCase;

        @Mock
        private JobRepository jobRepository;

        @Test
        @DisplayName("Should return empty list when not found jobs.")
        void should_return_empty_when_not_found_jobs() {

                try {
                        List<JobEntity> list = listAllJobByFilterUseCase.execute("");

                        assertThat(list.size()).isEqualTo(0);
                } catch (Exception e) {
                        assertThat(e.getMessage()).isEqualTo("O campo filtro não pode ser vazio.");
                }

        }

}
