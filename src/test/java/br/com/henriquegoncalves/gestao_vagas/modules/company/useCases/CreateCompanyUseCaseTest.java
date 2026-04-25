package br.com.henriquegoncalves.gestao_vagas.modules.company.useCases;

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

import br.com.henriquegoncalves.gestao_vagas.exceptions.UserFoundException;
import br.com.henriquegoncalves.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCompanyUseCaseTest {

    @InjectMocks
    private CreateCompanyUseCase createCompanyUseCase;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void should_be_return_user_found_exception_if_company_exists() {

        CompanyEntity entity = CompanyEntity.builder().username("company").email("company@email.com").build();

        when(companyRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail()))
                .thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> createCompanyUseCase.execute(entity))
                .isInstanceOf(UserFoundException.class);

    }

    @Test
    public void should_be_create_company() {

        CompanyEntity entity = CompanyEntity.builder().username("company").email("company@email.com")
                .password("1234567891011").build();

        when(companyRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(entity.getPassword())).thenReturn("senhaCrypto");

                when(companyRepository.save(entity)).thenReturn(entity);


        CompanyEntity result = createCompanyUseCase.execute(entity);

        assertThat(result).isNotNull();
        



    }

}
