package br.com.sistema.coworking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.sistema.coworking.DTO.Visitante.AtualizarVisitante;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.TipoVisitante;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import br.com.sistema.coworking.Service.VisitanteService;

@ExtendWith(MockitoExtension.class)
public class VisitanteServiceTest {

    @Mock
    private VisitanteRepository visitanteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private VisitanteService visitanteService;

    private Visitante criarVisitante() {
        Visitante visitanteCriado = new Visitante(1, "Pedro Henrique", "00000000000", "pedro@gmail.com", "123456789",
                "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), TipoVisitante.VISITANTE, true);
        return visitanteCriado;
    }

    @Test
    public void testSucessoAoAtualizarVisitante() {

        Visitante visitante = criarVisitante();

        when(visitanteRepository.findByCpf(visitante.getCpf())).thenReturn(Optional.of(visitante));

        AtualizarVisitante visitanteAtualizado = new AtualizarVisitante("00000000000", "Nome Atualizado",
                "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste");

        visitanteService.atualizarDados(visitanteAtualizado, null);

        verify(visitanteRepository, times(1)).save(visitante);
        assertEquals("Nome Atualizado", visitante.getNomeCompleto());
    }

    @Test
    public void testSucessoAoDesativarVisitante() {

        Visitante visitante = criarVisitante();

        when(visitanteRepository.findByCpf(visitante.getCpf())).thenReturn(Optional.of(visitante));

        AtualizarVisitante visitanteAtualizado = new AtualizarVisitante("00000000000", "Nome Atualizado",
                "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste");

        visitanteService.desativarVisitante(visitanteAtualizado);

        verify(visitanteRepository, times(1)).save(visitante);
        assertFalse(visitante.isAtivo());
    }

    @Test
    public void testSucessoAoAtivarVisitante() {

        Visitante visitante = criarVisitante();

        when(visitanteRepository.findByCpf(visitante.getCpf())).thenReturn(Optional.of(visitante));

        AtualizarVisitante visitanteAtualizado = new AtualizarVisitante("00000000000", "Nome Atualizado",
                "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste");

        visitanteService.ativarVisitante(visitanteAtualizado);

        verify(visitanteRepository, times(1)).save(visitante);
        assertTrue(visitante.isAtivo());
    }

    @Test
    public void testSucessoAoObterVisitante() {

        Visitante visitante = criarVisitante();

        when(visitanteRepository.findByCpf(visitante.getCpf())).thenReturn(Optional.of(visitante));

        AtualizarVisitante visitanteAtualizado = new AtualizarVisitante("00000000000", "Nome Atualizado",
                "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste");

        Visitante visitanteRetornado = visitanteService.obterDadosVisitante(visitanteAtualizado);

        assertEquals(visitante, visitanteRetornado);
    }

    @Test
    public void testSucessoAoListarVisitantes() {

        Visitante visitante = criarVisitante();

        List<Visitante> visitantes = List.of(visitante);
        Page<Visitante> visitantesPage = new PageImpl<>(visitantes, Pageable.unpaged(), 1);

        when(visitanteRepository.findAll(any(Pageable.class))).thenReturn(visitantesPage);

        Page<Visitante> visitantesObtidos = visitanteService.listarVisitantes(PageRequest.of(0, 10));

        assertEquals(1, visitantesObtidos.getContent().size());
    }
}
