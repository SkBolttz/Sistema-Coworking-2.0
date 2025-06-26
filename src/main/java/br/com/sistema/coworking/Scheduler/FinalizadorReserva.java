package br.com.sistema.coworking.Scheduler;

import java.time.LocalDateTime;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Enum.StatusReserva;
import br.com.sistema.coworking.Repository.ReservaRepository;

@Service
public class FinalizadorReserva {

    private final ReservaRepository reservaRepository;

    public FinalizadorReserva(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void finalizarReservas() {

        List<Reserva> reservasParaFinalizar = reservaRepository.findAll()
                .stream()
                .filter(reserva -> reserva.getDataFim().isBefore(LocalDateTime.now())
                        && reserva.getStatus() == StatusReserva.ATIVA)
                .toList();

        reservasParaFinalizar.forEach(reserva -> reserva.setStatus(StatusReserva.FINALIZADA));
        reservaRepository.saveAll(reservasParaFinalizar);
    }
}