package br.com.sistema.coworking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Collections;
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
import br.com.sistema.coworking.DTO.Reserva.AtualizarReservaDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.StatusReserva;
import br.com.sistema.coworking.Enum.TipoReserva;
import br.com.sistema.coworking.Enum.TipoSala;
import br.com.sistema.coworking.Enum.TipoVisitante;
import br.com.sistema.coworking.Repository.EstacaoRepository;
import br.com.sistema.coworking.Repository.ReservaRepository;
import br.com.sistema.coworking.Repository.SalaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import br.com.sistema.coworking.Service.ReservaService;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private SalaRepository salaRepository;
    @Mock
    private EstacaoRepository estacaoRepository;
    @Mock
    private VisitanteRepository visitanteRepository;
    @InjectMocks
    private ReservaService reservaService;

    private Visitante criarVisitante() {
        Visitante visitante = new Visitante(Long.valueOf(1), "Pedro Henrique", "00000000000", "pedro@gmail.com",
                "123456789", "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), TipoVisitante.VISITANTE,
                true);

        return visitante;
    }

    private Sala criarSala() {
        Sala sala = new Sala(Long.valueOf(1), "Nome A", "Descricao A", 1, true, TipoSala.EVENTO, "Foto A",
                "Localizacao A", LocalDateTime.now(), LocalDateTime.now());
        return sala;
    }

    private Estacao criarEstacao(Sala sala) {
        Estacao estacao = new Estacao(Long.valueOf(1), "Identificador A", "Descricao A", true, true, true, true, sala,
                "Foto A", LocalDateTime.now(), LocalDateTime.now(), true);
        return estacao;
    }

    private LocalDateTime horarioFim() {
        LocalDateTime horarioFim = LocalDateTime.now().plusHours(10);
        return horarioFim;
    }

    private Reserva criarReservaSala(Sala sala, Visitante visitante) {
        Reserva reserva = new Reserva(Long.valueOf(1), TipoReserva.SALA, sala, null, visitante, LocalDateTime.now(),
                horarioFim(), StatusReserva.ATIVA, true, "Reserva criada!");
        return reserva;
    }

    private Reserva criarReservaEstacao(Estacao estacao, Visitante visitante) {
        Reserva reserva = new Reserva(Long.valueOf(1), TipoReserva.SALA, null, estacao, visitante, LocalDateTime.now(),
                horarioFim(), StatusReserva.ATIVA, true, "Reserva criada!");
        return reserva;
    }

    @Test
    public void testSucessoAoCriarUmaReservaComSala() {

        Sala sala = criarSala();
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaSala(sala, visitante);

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));
        when(visitanteRepository.findById(visitante.getId())).thenReturn(Optional.of(visitante));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        reservaService.criarReserva(reserva);

        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testSucessoAoCriarUmaReservaComEstacao() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        when(estacaoRepository.findById(estacao.getId())).thenReturn(Optional.of(estacao));
        when(visitanteRepository.findById(visitante.getId())).thenReturn(Optional.of(visitante));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        reservaService.criarReserva(reserva);

        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testSucessoAoAtualizarUmaReserva() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AtualizarReservaDTO reservaDTO = new AtualizarReservaDTO(1L, TipoReserva.SALA, "Observacao atualizada",
                LocalDateTime.now(), horarioFim(),
                visitante);

        reservaService.atualizarReserva(reservaDTO);

        verify(reservaRepository, times(1)).save(reserva);
        assertEquals("Observacao atualizada", reserva.getObservacao());
    }

    @Test
    public void testSucessoAoCancelarUmaReserva() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AtualizarReservaDTO reservaDTO = new AtualizarReservaDTO(1L, TipoReserva.SALA, "Observacao atualizada",
                LocalDateTime.now(), horarioFim(),
                visitante);

        reservaService.cancelarReserva(reservaDTO);

        verify(reservaRepository, times(1)).save(reserva);
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus());
    }

    @Test
    public void testSucessoAoListarReservas() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        Page<Reserva> reservas = new PageImpl<>(Collections.singletonList(reserva));
        when(reservaRepository.findAll(any(Pageable.class))).thenReturn(reservas);

        Page<Reserva> resultado = reservaService.listarReservas(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
    }

    @Test
    public void testSucessoAoListarReservasAtivas() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        Page<Reserva> reservas = new PageImpl<>(Collections.singletonList(reserva));
        when(reservaRepository.findByStatus(any(StatusReserva.class), any(Pageable.class))).thenReturn(reservas);

        Page<Reserva> resultado = reservaService.listarReservasAtivas(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
        assertEquals(reservas.getContent().get(0).getStatus(), resultado.getContent().get(0).getStatus());
    }

    @Test
    public void testSucessoAoListarReservasPorUsuario() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);
        Visitante visitante = criarVisitante();
        Reserva reserva = criarReservaEstacao(estacao, visitante);

        List<Reserva> reservas = Collections.singletonList(reserva);
        when(reservaRepository.findByVisitanteId(visitante.getId())).thenReturn(reservas);
        List<Reserva> resultado = reservaService
                .listarReservasPorUsuario(new AtualizarReservaDTO(0, null, null, null, null, visitante));

        assertEquals(1, resultado.size());
        assertEquals(reservas.get(0), resultado.get(0));
    }
}
