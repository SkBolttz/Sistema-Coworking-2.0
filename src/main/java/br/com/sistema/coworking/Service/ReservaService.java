package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Reserva.AtualizarReservaDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.StatusReserva;
import br.com.sistema.coworking.Enum.TipoReserva;
import br.com.sistema.coworking.Exception.Records.Estacao.EstacaoReservaException;
import br.com.sistema.coworking.Exception.Records.Reserva.ReservaException;
import br.com.sistema.coworking.Exception.Records.Sala.ReservaSalaException;
import br.com.sistema.coworking.Exception.Records.Visitante.VisitanteException;
import br.com.sistema.coworking.Repository.EstacaoRepository;
import br.com.sistema.coworking.Repository.ReservaRepository;
import br.com.sistema.coworking.Repository.SalaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;
    private final EstacaoRepository estacaoRepository;
    private final VisitanteRepository visitanteRepository;

    public ReservaService(ReservaRepository reservaRepository, SalaRepository salaRepository,
            EstacaoRepository estacaoRepository, VisitanteRepository visitanteRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
        this.estacaoRepository = estacaoRepository;
        this.visitanteRepository = visitanteRepository;
    }

    public void criarReserva(Reserva reserva) {

        verificarDisponibilidadeSala(reserva);
        verificarDisponibilidadeEstacao(reserva);
        verificarReservaExistenteDoVisitante(reserva);

        reserva.setStatus(StatusReserva.ATIVA);
        reserva.setConfirmada(true);

        reservaRepository.save(reserva);
    }

    public void atualizarReserva(AtualizarReservaDTO dto) {

        Reserva reservaExiste = reservaRepository.findById(dto.id()).get();

        verificarAtualizacaoPermitida(reservaExiste, dto);
        aplicarAlteracoesNaReserva(reservaExiste, dto);

        reservaRepository.save(reservaExiste);
    }

    public void cancelarReserva(AtualizarReservaDTO dto) {

        Reserva reservaExiste = reservaRepository.findById(dto.id()).get();

        verificarCancelamentoPermitida(reservaExiste, dto);
        aplicarCancelamentoNaReserva(reservaExiste, dto);

        reservaRepository.save(reservaExiste);
    }

    public Page<Reserva> listarReservas(Pageable pageable) {

        return listagemDeReservas(pageable);
    }

    public Page<Reserva> listarReservasAtivas(Pageable pageable) {

        return listagemDeReservasAtivas(pageable);
    }

    public List<Reserva> listarReservasPorUsuario(AtualizarReservaDTO reserva) {

        return listagemDeReservasPorUsuario(reserva);
    }

    public Reserva obterReserva(AtualizarReservaDTO reserva) {

        return obterReservaId(reserva);
    }

    private void verificarDisponibilidadeSala(Reserva reserva) {

        if (reserva.getSala() != null) {

            Optional<Sala> salaIndisponivel = salaRepository.findById(reserva.getSala().getId());

            if (salaIndisponivel.get().isDisponivel() == false) {
                throw new ReservaSalaException("Sala indisponível no momento, por favor escolha outra sala!", "");
            }
        }
    }

    private void verificarDisponibilidadeEstacao(Reserva reserva) {

        if (reserva.getEstacao() != null) {
            Optional<Estacao> estacaoIndisponivel = estacaoRepository.findById(reserva.getEstacao().getId());

            if (estacaoIndisponivel.get().isDisponivel() == false) {
                throw new EstacaoReservaException(
                        "Estação indisponível no momento, por favor escolha outra estação!",
                        "");
            }
        }
    }

    private void verificarReservaExistenteDoVisitante(Reserva reserva) {

        Optional<Visitante> visitanteJaComReserva = visitanteRepository.findById(reserva.getVisitante().getId());

        reservaRepository.findAll().forEach(reservaExistente -> {
            if (reservaExistente.getVisitante().getId() == visitanteJaComReserva.get().getId()
                    && reservaExistente.getStatus() == StatusReserva.ATIVA) {
                throw new VisitanteException("Visitante já possui uma reserva!", "");
            }
        });
    }

    private void verificarAtualizacaoPermitida(Reserva reserva, AtualizarReservaDTO dto) {

        if (reserva.getVisitante().getId() != dto.visitante().getId()) {
            throw new ReservaException("Visitante diferente não pode atualizar esta reserva.", "");
        }

        if (reserva.getStatus() != StatusReserva.ATIVA) {
            throw new ReservaException("Reserva não está ativa e não pode ser atualizada.", "");
        }

        if (!reserva.isConfirmada()) {
            throw new ReservaException("Reserva não confirmada não pode ser atualizada.", "");
        }

        LocalDateTime agora = LocalDateTime.now();

        if (reserva.getDataFim().isBefore(agora)) {
            throw new ReservaException("Reserva já terminou e não pode ser atualizada.", "");
        }

        if (dto.dataInicio().isAfter(dto.dataFim())) {
            throw new ReservaException("Data de início não pode ser depois da data de fim.", "");
        }

        if (dto.dataFim().isBefore(agora)) {
            throw new ReservaException("Data de fim da reserva não pode ser no passado.", "");
        }

        if (dto.dataInicio().isBefore(reserva.getDataInicio())) {
            throw new ReservaException("Data de início da atualização não pode ser anterior à data atual da reserva.",
                    "");
        }
    }

    private void aplicarAlteracoesNaReserva(Reserva reservaExiste, AtualizarReservaDTO dto) {
        reservaExiste.setDataInicio(dto.dataInicio());
        reservaExiste.setDataFim(dto.dataFim());
        reservaExiste.setObservacao(dto.observacao());
        reservaExiste.setTipo(TipoReserva.valueOf(dto.tipo().name()));
    }

    private void verificarCancelamentoPermitida(Reserva reservaExiste, AtualizarReservaDTO dto) {

        if (reservaExiste.getVisitante().getId() != dto.visitante().getId()) {
            throw new ReservaException("Visitante diferente não pode atualizar esta reserva.", "");
        }

        if (reservaExiste.isConfirmada() == false) {
            throw new ReservaException("Reserva nao confirmada nao pode ser cancelada", "");
        }

        if (reservaExiste.getStatus() != StatusReserva.ATIVA) {
            throw new ReservaException("Reserva nao ativa nao pode ser cancelada", "");
        }
    }

    private void aplicarCancelamentoNaReserva(Reserva reservaExiste, AtualizarReservaDTO dto) {
        reservaExiste.setConfirmada(false);
        reservaExiste.setStatus(StatusReserva.CANCELADA);
    }

    private Page<Reserva> listagemDeReservas(Pageable pageable) {

        return reservaRepository.findAll(pageable);
    }

    private Page<Reserva> listagemDeReservasAtivas(Pageable pageable) {

        return reservaRepository.findByStatus(StatusReserva.ATIVA, pageable);
    }

    private List<Reserva> listagemDeReservasPorUsuario(AtualizarReservaDTO reserva) {

        System.out.println(reserva.visitante().getId());
        return reservaRepository.findByVisitanteId(reserva.visitante().getId());
    }

    private Reserva obterReservaId(AtualizarReservaDTO reserva) {

        Optional<Reserva> reservaExiste = reservaRepository.findById(reserva.id());

        if (reservaExiste.isEmpty()) {
            throw new ReservaException("Reserva nao encontrada.", "");
        }

        return reservaExiste.get();
    }
}
