package br.com.sistema.coworking.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Sala.AtualizarSalaDTO;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Repository.SalaRepository;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void criarSala(Sala sala) {

        Sala salaExiste = salaRepository.findByNome(sala.getNome());

        if (salaExiste != null) {
            throw new RuntimeException("Sala com nome " + sala.getNome() + " ja cadastrada!");
        }

        if (sala.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade de pessoas deve ser maior que 0!");
        }

        salaRepository.save(sala);
    }

    public void atualizarSala(AtualizarSalaDTO atualizarSala) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

        if (salaExiste.getNome().equals(atualizarSala.nome())) {
            throw new RuntimeException("Sala com nome " + atualizarSala.nome() + " ja cadastrada!");
        }

        if (atualizarSala.nome() != null) {
            salaExiste.setNome(atualizarSala.nome());
        }

        if (atualizarSala.descricao() != null) {
            salaExiste.setDescricao(atualizarSala.descricao());
        }

        if (atualizarSala.quantidade() != 0 && atualizarSala.quantidade() > 0) {
            salaExiste.setQuantidade(atualizarSala.quantidade());
        }

        if (atualizarSala.tipo() != null) {
            salaExiste.setTipo(atualizarSala.tipo());
        }

        if (atualizarSala.localizacao() != null) {
            salaExiste.setLocalizacao(atualizarSala.localizacao());
        }

        salaRepository.save(salaExiste);
    }

    public void ativarSala(AtualizarSalaDTO atualizarSala) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

        salaExiste.setDisponivel(true);
        salaRepository.save(salaExiste);
    }

    public void desativarSala(AtualizarSalaDTO atualizarSala) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));

        salaExiste.setDisponivel(false);
        salaRepository.save(salaExiste);
    }

    public Sala obterDadosSala(AtualizarSalaDTO atualizarSala) {
        return salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada!"));
    }

    public List<Sala> listarSalas() {
        return salaRepository.findAll()
                .stream()
                .toList();
    }

    public List<Sala> listarSalasDisponiveis() {
        return salaRepository.findAll()
                .stream()
                .filter(e -> e.isDisponivel() == true)
                .toList();
    }

    public List<Sala> listarSalasIndisponiveis() {
        return salaRepository.findAll()
                .stream()
                .filter(e -> e.isDisponivel() == false)
                .toList();
    }
}
