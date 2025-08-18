package br.com.sistema.coworking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.TipoVisitante;
import br.com.sistema.coworking.Repository.EmpresaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import br.com.sistema.coworking.Service.LoginService;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private VisitanteRepository visitanteRepository;
    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private LoginService loginService;

    private Visitante criarVisitante() {

        Visitante visitante = new Visitante(Long.valueOf(1), "Pedro Henrique", "00000000000", "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), TipoVisitante.VISITANTE,
                true);

        return visitante;
    }

    private Visitante criarFuncionario() {

        Visitante visitante = new Visitante(Long.valueOf(1), "Pedro Henrique", "00000000000", "pedro@coworking.com.br",
                "123456789", "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), null,
                true);

        return visitante;
    }

    private Visitante criarAdmin() {

        Visitante visitante = new Visitante(Long.valueOf(1), "Pedro Henrique", "00000000000",
                "pedro@coworkingadmin.com.br",
                "123456789", "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), null,
                true);

        return visitante;
    }

    @Test
    public void testSucessoAoRealizarRegistroVisitante() {

        Visitante visitante = criarVisitante();

        loginService.registro(visitante, null);

        verify(visitanteRepository, times(1)).save(visitante);
        assertEquals(visitante.getTipo(), TipoVisitante.VISITANTE);
    }

    @Test
    public void testSucessoAoRealizarRegistroFuncionario() {

        Visitante visitante = criarFuncionario();

        loginService.registro(visitante, null);

        verify(visitanteRepository, times(1)).save(visitante);
        assertEquals(visitante.getTipo(), TipoVisitante.FUNCIONARIO);
    }

    @Test
    public void testSucessoAoRealizarRegistroAdmin() {

        Visitante visitante = criarAdmin();

        loginService.registro(visitante, null);

        verify(visitanteRepository, times(1)).save(visitante);
        assertEquals(visitante.getTipo(), TipoVisitante.ADMIN);
    }
}
