package br.com.henriquegoncalves.gestao_vagas.modules.company.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.henriquegoncalves.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.henriquegoncalves.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.henriquegoncalves.gestao_vagas.modules.company.repositories.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class AuthCompanyUseCaseTest {

    @InjectMocks
    private AuthCompanyUseCase authCompanyUseCase;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
void setup() {
    ReflectionTestUtils.setField(authCompanyUseCase, "secretKey", "test-secret");
}

    @Test
    public void should_not_be_create_new_company_and_return_exception_username_not_found() {
        AuthCompanyDTO companyEntity = AuthCompanyDTO.builder().username("henrique").build();

        when(companyRepository.findByUsername(companyEntity.getUsername())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authCompanyUseCase.execute(companyEntity))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    public void should_not_be_create_new_company_if_password_is_wrong() {
        AuthCompanyDTO dtoEntity = AuthCompanyDTO.builder().username("henrique").password("1234567891011").build();

        CompanyEntity company = CompanyEntity.builder()
        .password("senhaCriptografada")
        .build();

        when(companyRepository.findByUsername(dtoEntity.getUsername()))
                .thenReturn(Optional.of(company));

        when(passwordEncoder.matches(dtoEntity.getPassword(),"senhaCriptografada")).thenReturn(false);

          assertThatThrownBy(() -> authCompanyUseCase.execute(dtoEntity))
                .isInstanceOf(AuthenticationException.class);

    }

     @Test
    public void should_be_create_new_company() throws Exception  {
        AuthCompanyDTO dtoEntity = AuthCompanyDTO.builder().username("henrique").password("1234567891011").build();

        CompanyEntity company = CompanyEntity.builder()
        .password("1234567891011")
        .id(UUID.randomUUID())
        .build();

        when(companyRepository.findByUsername(dtoEntity.getUsername()))
                .thenReturn(Optional.of(company));

        when(passwordEncoder.matches(dtoEntity.getPassword(),company.getPassword())).thenReturn(true);

        AuthCompanyResponseDTO result = authCompanyUseCase.execute(dtoEntity);

        assertThat(result).isNotNull();
        assertThat(result.getAccess_token()).isNotBlank();
        assertThat(result.getExpries_in()).isNotNull();

    }

}
