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

import br.com.sistema.coworking.DTO.Sala.AtualizarSalaDTO;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Enum.TipoSala;
import br.com.sistema.coworking.Repository.SalaRepository;
import br.com.sistema.coworking.Service.SalaService;

@ExtendWith(MockitoExtension.class)
public class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;
    @InjectMocks
    private SalaService salaService;

    private Sala criarSala() {
        Sala sala = new Sala(Long.valueOf(1), "Nome A", "Descricao A", 1, true, TipoSala.EVENTO,
                "Foto A", "Localizacao A", LocalDateTime.now(), LocalDateTime.now());
        return sala;
    }

    private Sala criarSalaDesativada() {
        Sala sala = new Sala(Long.valueOf(1), "Nome A", "Descricao A", 1, false, TipoSala.EVENTO,
                "Foto A", "Localizacao A", LocalDateTime.now(), LocalDateTime.now());
        return sala;
    }

    @Test
    public void testSucessoAoCriarSala() {

        Sala sala = criarSala();

        salaService.criarSala(sala, null);

        verify(salaRepository, times(1)).save(sala);
    }

    @Test
    public void testSucessoAoAtualizarSala() {

        Sala sala = criarSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        AtualizarSalaDTO salaDTO = new AtualizarSalaDTO(Long.valueOf(1), "Sala A atualizada", "Descricao A", 1, true,
                null, null,
                null);

        salaService.atualizarSala(salaDTO, null);

        verify(salaRepository, times(1)).save(sala);
        assertEquals("Sala A atualizada", sala.getNome());
    }

    @Test
    public void testSucessoAoAtivarSala() {

        Sala sala = criarSalaDesativada();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        AtualizarSalaDTO salaDTO = new AtualizarSalaDTO(Long.valueOf(1), "Sala A atualizada", "Descricao A", 1, true,
                null, null,
                null);

        salaService.ativarSala(salaDTO);

        verify(salaRepository, times(1)).save(sala);
        assertTrue(sala.isDisponivel());
    }

    @Test
    public void testSucessoAoDesativarSala() {

        Sala sala = criarSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        AtualizarSalaDTO salaDTO = new AtualizarSalaDTO(Long.valueOf(1), "Sala A atualizada", "Descricao A", 1, true,
                null, null,
                null);

        salaService.desativarSala(salaDTO);

        verify(salaRepository, times(1)).save(sala);
        assertFalse(sala.isDisponivel());
    }

    @Test
    public void testSucessoAoObterSala() {

        Sala sala = criarSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        AtualizarSalaDTO salaDTO = new AtualizarSalaDTO(Long.valueOf(1), "Sala A atualizada", "Descricao A", 1, true,
                null, null,
                null);

        Sala salaObtida = salaService.obterDadosSala(salaDTO);

        assertEquals(sala, salaObtida);
    }

    @Test
    public void testSucessoAoListarSalas() {

        Sala sala = criarSala();

        List<Sala> salas = List.of(sala);
        Page<Sala> salasPage = new PageImpl<>(salas, Pageable.unpaged(), 1);

        when(salaRepository.findAll(any(Pageable.class))).thenReturn(salasPage);

        Page<Sala> salasObtidas = salaService.listarSalas(PageRequest.of(0, 10));

        assertEquals(1, salasObtidas.getContent().size());
    }

    @Test
    public void testSucessoAoListarSalasAtivas() {

        Sala sala = criarSala();

        List<Sala> salas = List.of(sala);
        Page<Sala> salasPage = new PageImpl<>(salas, Pageable.unpaged(), 1);

        when(salaRepository.findByDisponivelTrue(any(Pageable.class))).thenReturn(salasPage);

        Page<Sala> salasObtidas = salaService.listarSalasDisponiveis(PageRequest.of(0, 10));

        assertEquals(1, salasObtidas.getContent().size());
        assertTrue(salasObtidas.getContent().get(0).isDisponivel());
    }

    @Test
    public void testSucessoAoListarSalasIndisponiveis() {

        Sala sala = criarSalaDesativada(); 

        List<Sala> salas = List.of(sala);
        Page<Sala> salasPage = new PageImpl<>(salas, Pageable.unpaged(), 1);

        when(salaRepository.findByDisponivelFalse(any(Pageable.class))).thenReturn(salasPage);

        Page<Sala> salasObtidas = salaService.listarSalasIndisponiveis(PageRequest.of(0, 10));

        assertEquals(1, salasObtidas.getContent().size());
        assertFalse(salasObtidas.getContent().get(0).isDisponivel());
    }
}
