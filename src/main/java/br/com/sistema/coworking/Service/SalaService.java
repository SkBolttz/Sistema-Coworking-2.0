package br.com.sistema.coworking.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.sistema.coworking.DTO.Sala.AtualizarSalaDTO;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Exception.Records.Sala.AtualizarSalaException;
import br.com.sistema.coworking.Exception.Records.Sala.SalaException;
import br.com.sistema.coworking.Repository.EstacaoRepository;
import br.com.sistema.coworking.Repository.SalaRepository;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    private final EstacaoRepository estacaoRepository;

    public SalaService(SalaRepository salaRepository, EstacaoRepository estacaoRepository) {
        this.salaRepository = salaRepository;
        this.estacaoRepository = estacaoRepository;
    }

    public void criarSala(Sala sala, MultipartFile file) {

        verificandoCadastroSala(sala);

        sala.setDataCriacao(LocalDateTime.now());
        sala.setDisponivel(true);

        if (file != null && !file.isEmpty()) {
            try {
                String diretorio = new File("imagens/salas").getAbsolutePath();

                String nomeArquivoSeguro = file.getOriginalFilename()
                        .replaceAll(" ", "_")
                        .replaceAll("\\(", "_")
                        .replaceAll("\\)", "");

                File pasta = new File(diretorio);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                File destino = new File(pasta, nomeArquivoSeguro);
                file.transferTo(destino);

                sala.setFotoUrl(nomeArquivoSeguro);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
            }
        }

        salaRepository.save(sala);
    }

    public void atualizarSala(AtualizarSalaDTO atualizarSala, MultipartFile file) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new SalaException("Sala não encontrada!", ""));

        if (file != null && !file.isEmpty()) {
            try {
                String diretorio = new File("imagens/salas").getAbsolutePath();

                String nomeArquivoSeguro = file.getOriginalFilename()
                        .replaceAll(" ", "_")
                        .replaceAll("\\(", "_")
                        .replaceAll("\\)", "");

                File pasta = new File(diretorio);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                File destino = new File(pasta, nomeArquivoSeguro);
                file.transferTo(destino);

                salaExiste.setFotoUrl(nomeArquivoSeguro);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
            }
        }

        verificandoAtualizacao(atualizarSala, salaExiste);
        aplicandoAtualizacao(atualizarSala, salaExiste);

        salaRepository.save(salaExiste);
    }

    public void ativarSala(AtualizarSalaDTO atualizarSala) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new SalaException("Sala não encontrada!", ""));

        if (estacaoRepository.findBySala(salaExiste.getId()).isPresent()) {
            throw new SalaException("Sala vinculada a uma estação!", "");
        }


        salaExiste.setDisponivel(true);
        salaRepository.save(salaExiste);
    }

    public void desativarSala(AtualizarSalaDTO atualizarSala) {

        Sala salaExiste = salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new SalaException("Sala não encontrada!", ""));

        salaExiste.setDisponivel(false);
        salaRepository.save(salaExiste);
    }

    public Sala obterDadosSala(AtualizarSalaDTO atualizarSala) {

        return obterDados(atualizarSala);
    }

    public Page<Sala> listarSalas(Pageable pageable) {

        return listagemSalas(pageable);
    }

    public Page<Sala> listarSalasDisponiveis(Pageable pageable) {

        return listagemSalasDisponiveis(pageable);
    }

    public Page<Sala> listarSalasIndisponiveis(Pageable pageable) {

        return listagemSalasIndisponiveis(pageable);
    }

    private void verificandoCadastroSala(Sala sala) {

        Sala salaExiste = salaRepository.findByNome(sala.getNome());

        if (salaExiste != null) {
            throw new SalaException("Sala com nome " + sala.getNome() + " ja cadastrada!", "");
        }

        if (sala.getQuantidade() <= 0) {
            throw new SalaException("Quantidade de pessoas deve ser maior que 0!", "");
        }
    }

    private void verificandoAtualizacao(AtualizarSalaDTO atualizarSala, Sala salaExiste) {

        if (atualizarSala.nome() != null && !atualizarSala.nome().equals(salaExiste.getNome())) {
            Sala salaComMesmoNome = salaRepository.findByNome(atualizarSala.nome());

            if (salaComMesmoNome != null && salaComMesmoNome.getId() != salaExiste.getId()) {
                throw new AtualizarSalaException("Sala com nome " + atualizarSala.nome() + " já cadastrada!", "");
            }
        }
    }

    private void aplicandoAtualizacao(AtualizarSalaDTO atualizarSala, Sala salaExiste) {

        if (atualizarSala.nome() != null) {
            salaExiste.setNome(atualizarSala.nome());
        }

        if (atualizarSala.descricao() != null) {
            salaExiste.setDescricao(atualizarSala.descricao());
        }

        if (atualizarSala.quantidade() > 0) {
            salaExiste.setQuantidade(atualizarSala.quantidade());
        }

        if (atualizarSala.tipo() != null) {
            salaExiste.setTipo(atualizarSala.tipo());
        }

        if (atualizarSala.localizacao() != null) {
            salaExiste.setLocalizacao(atualizarSala.localizacao());
        }
    }

    private Sala obterDados(AtualizarSalaDTO atualizarSala) {

        return salaRepository.findById(atualizarSala.id())
                .orElseThrow(() -> new SalaException("Sala não encontrada!", ""));
    }

    private Page<Sala> listagemSalas(Pageable pageable) {

        return salaRepository.findAll(pageable);
    }

    private Page<Sala> listagemSalasDisponiveis(Pageable pageable) {

        return salaRepository.findByDisponivelTrue(pageable);
    }

    private Page<Sala> listagemSalasIndisponiveis(Pageable pageable) {

        return salaRepository.findByDisponivelFalse(pageable);
    }
}
