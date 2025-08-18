package br.com.sistema.coworking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import br.com.sistema.coworking.DTO.Estacao.AtualizarEstacaoDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Enum.TipoSala;
import br.com.sistema.coworking.Repository.EstacaoRepository;
import br.com.sistema.coworking.Repository.SalaRepository;
import br.com.sistema.coworking.Service.EstacaoService;

@ExtendWith(MockitoExtension.class)
public class EstacaoServiceTest {

    @Mock
    private EstacaoRepository estacaoRepository;
    @Mock
    private SalaRepository salaRepository;
    @InjectMocks
    private EstacaoService estacaoService;

    private Sala criarSala() {
        Sala sala = new Sala(Long.valueOf(1), "Nome A", "Descricao A", 1, true, TipoSala.EVENTO,
                "Foto A", "Localizacao A", LocalDateTime.now(), LocalDateTime.now());
        return sala;
    }

    private Estacao criarEstacao(Sala sala) {
        Estacao estacao = new Estacao(Long.valueOf(1), "Identificador A", "Descricao A", true, true, true, true, sala,
                "Foto A", LocalDateTime.now(), LocalDateTime.now(), true);
        return estacao;
    }

    private Estacao criarEstacaoDesativada(Sala sala) {
        Estacao estacao = new Estacao(Long.valueOf(1), "Identificador A", "Descricao A", true, true, true, true, sala,
                "Foto A", LocalDateTime.now(), LocalDateTime.now(), false);
        return estacao;
    }

    @Test
    public void testSucessoAoCadastrarEstacao() {
        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));
        when(estacaoRepository.save(estacao)).thenReturn(estacao);

        estacaoService.cadastrarEstacao(estacao, null);

        verify(estacaoRepository, times(1)).save(estacao);
    }

    @Test
    public void testSucessoAoAtualizarEstacao() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        when(estacaoRepository.findById(estacao.getId())).thenReturn(Optional.of(estacao));
        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));
        AtualizarEstacaoDTO estacaoDTO = new AtualizarEstacaoDTO(Long.valueOf(1), "Nome atualizado com sucesso", null,
                null, null, null, null, null, sala);

        estacaoService.atualizarEstacao(estacaoDTO, null);

        assertEquals(estacaoDTO.identificador(), "Nome atualizado com sucesso");
    }

    @Test
    public void testSucessoAoAtivarUmaEmpresa() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacaoDesativada(sala);

        when(estacaoRepository.findById(estacao.getId())).thenReturn(Optional.of(estacao));

        AtualizarEstacaoDTO estacaoDTO = new AtualizarEstacaoDTO(Long.valueOf(1), "Nome atualizado com sucesso", null,
                null, null, null, null, null, sala);

        estacaoService.ativarEstacao(estacaoDTO);

        assertEquals(estacao.isAtivo(), true);
    }

    @Test
    public void testSucessoAoDesativarUmaEmpresa() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        when(estacaoRepository.findById(estacao.getId())).thenReturn(Optional.of(estacao));

        AtualizarEstacaoDTO estacaoDTO = new AtualizarEstacaoDTO(Long.valueOf(1), "Nome atualizado com sucesso", null,
                null, null, null, null, null, sala);

        estacaoService.desativarEstacao(estacaoDTO);

        assertEquals(estacao.isAtivo(), false);
    }

    @Test
    public void testSucessoAoObterUmaEmpresa() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        when(estacaoRepository.findById(estacao.getId())).thenReturn(Optional.of(estacao));

        AtualizarEstacaoDTO estacaoDTO = new AtualizarEstacaoDTO(Long.valueOf(1), "Nome atualizado com sucesso", null,
                null, null, null, null, null, sala);

        Estacao estacaoObtida = estacaoService.obterEstacao(estacaoDTO);

        assertEquals(estacao, estacaoObtida);
    }

    @Test
    public void testSucessoAoListarEstacoes() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        Page<Estacao> estacoes = new PageImpl<>(Collections.singletonList(estacao));
        when(estacaoRepository.findAll(any(Pageable.class))).thenReturn(estacoes);

        Page<Estacao> resultado = estacaoService.listarEstacoes(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
    }

    @Test
    public void testSucessoAoListarEmpresasAtivas() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacao(sala);

        List<Estacao> estacaoAtiva = List.of(estacao);
        Page<Estacao> estacoes = new PageImpl<>(estacaoAtiva);

        when(estacaoRepository.findByAtivoTrue(any(Pageable.class))).thenReturn(estacoes);

        Page<Estacao> resultado = estacaoService.listarEstacoesAtivas(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
        assertTrue(resultado.getContent().get(0).isAtivo());
    }

    @Test
    public void testSucessoAoListarEmpresasDesativadas() {

        Sala sala = criarSala();
        Estacao estacao = criarEstacaoDesativada(sala);

        List<Estacao> estacaoDesativada = List.of(estacao);
        Page<Estacao> estacoes = new PageImpl<>(estacaoDesativada);

        when(estacaoRepository.findByAtivoFalse(any(Pageable.class))).thenReturn(estacoes);

        Page<Estacao> resultado = estacaoService.listarEstacoesInativas(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
        assertFalse(resultado.getContent().get(0).isAtivo());
    }
}
