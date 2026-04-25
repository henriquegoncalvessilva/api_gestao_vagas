package br.com.henriquegoncalves.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.henriquegoncalves.gestao_vagas.exceptions.UserFieldsNotCorrectException;
import br.com.henriquegoncalves.gestao_vagas.exceptions.UserFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

        @InjectMocks
        private CreateCandidateUseCase createCandidateUseCase;

        @Mock
        private CandidateRepository candidateRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Test
        public void should_be_able_create_candidate_whith_password_crypto() {
                CandidateEntity newUser = CandidateEntity.builder().name("name").email("example3@email.com")
                                .username("henrique")
                                .password("1234565646326346").build();
                when(candidateRepository.findByUsernameOrEmail(newUser.getUsername(), newUser.getEmail()))
                                .thenReturn(Optional.empty());

                when(this.passwordEncoder.encode(newUser.getPassword())).thenReturn("passwordCrypto");

                when(candidateRepository.save(newUser)).thenReturn(newUser);

                CandidateEntity result = createCandidateUseCase.execute(newUser);

                assertThat(result).isNotNull();
                assertThat(result.getEmail()).isEqualTo("example3@email.com");

        }

        @Test
        public void should_not_be_able_create_candidate_when_username_already_exists() {
                CandidateEntity newUser = CandidateEntity.builder().name("name").email("example3@email.com")
                                .username("henrique")
                                .password("1234565646326346").build();
                when(candidateRepository.findByUsernameOrEmail(newUser.getUsername(), newUser.getEmail()))
                                .thenReturn(Optional.of((new CandidateEntity())));

                assertThatThrownBy(() -> createCandidateUseCase.execute(newUser))
                                .isInstanceOf(UserFoundException.class);

        }

        @Test
        public void should_not_be_able_create_new_candidate_when_email_fields_empty() {
                CandidateEntity newUser2 = CandidateEntity.builder().name("name").email(null).username("dssdsddsd")
                                .password("1234567891011").build();

                assertThatThrownBy(() -> createCandidateUseCase.execute(newUser2))
                                .isInstanceOf(UserFieldsNotCorrectException.class);

        }

        @Test
        public void should_not_be_able_create_new_candidate_when_username_fields_empty() {
                CandidateEntity newUser2 = CandidateEntity.builder().name("name").email("eee@email.com").username(null)
                                .password("1234567891011").build();

                assertThatThrownBy(() -> createCandidateUseCase.execute(newUser2))
                                .isInstanceOf(UserFieldsNotCorrectException.class);

        }

        @Test
        public void should_be_return_user_found_exception_if_user_exists() {
                CandidateEntity user = CandidateEntity.builder().name("name").email("email@email.com")
                                .username("dssdsddsd")
                                .password("1234567891011").build();

                when(candidateRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()))
                                .thenReturn(Optional.of(user));

                assertThatThrownBy(() -> createCandidateUseCase.execute(user))
                                .isInstanceOf(UserFoundException.class);

        }

}
