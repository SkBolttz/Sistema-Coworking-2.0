package br.com.sistema.coworking.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.DTO.Reserva.AtualizarReservaDTO;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Repository.ReservaRepository;
import jakarta.validation.Valid;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void criarReserva(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criarReserva'");
    }

    public void atualizarReserva(AtualizarReservaDTO reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarReserva'");
    }

    public void cancelarReserva(AtualizarReservaDTO reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarReserva'");
    }

    public List<Reserva> listarReservas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarReservas'");
    }

    public List<Reserva> listarReservasAtivas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarReservasAtivas'");
    }

    public List<Reserva> listarReservasPorUsuario(AtualizarReservaDTO reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarReservasPorUsuario'");
    }

    public Object obterReserva(AtualizarReservaDTO reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterReserva'");
    }
}
