package br.com.sistema.coworking;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import br.com.sistema.coworking.DTO.Empresa.AtualizarEmpresaDTO;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Entity.Endereco;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.AprovacaoStatus;
import br.com.sistema.coworking.Enum.TipoVisitante;
import br.com.sistema.coworking.Exception.Records.Empresa.AtualizarEmpresaException;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Repository.EmpresaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import br.com.sistema.coworking.Service.EmpresaService;
import br.com.sistema.coworking.Service.EnderecoService;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private VisitanteRepository visitanteRepository;

    @InjectMocks
    private EmpresaService empresaService;

    private Endereco criarEndereco() {

        Endereco endereco = new Endereco(1L, "rua", "89031576", "cidade", "estado", "pais", null);

        return endereco;
    }

    private Visitante criarVisitante() {

        Visitante visitanteCriado = new Visitante(1, "Pedro Henrique", "00000000000", "pedro@gmail.com", "123456789",
                "0000000000", "documento.png", "obs:teste", LocalDateTime.now(), TipoVisitante.VISITANTE, true);

        return visitanteCriado;

    }

    private Empresa criarEmpresaAtiva(Visitante visitante, Endereco endereco) {
        return new Empresa(1L, "Empresa Teste", "Razao Social Teste", cnpj,
                "0000000000", "0000000000", visitante, null, endereco,
                LocalDateTime.now(), LocalDateTime.now(), AprovacaoStatus.PENDENTE, true);
    }

    private Empresa criarEmpresaInativa(Visitante visitante, Endereco endereco) {
        return new Empresa(1L, "Empresa Teste", "Razao Social Teste", cnpj,
                "0000000000", "0000000000", visitante, null, endereco,
                LocalDateTime.now(), LocalDateTime.now(), AprovacaoStatus.PENDENTE, false);
    }

    private final String cnpj = "00000000000000";

    // Teste com sucesso
    @Test
    public void testCadastroSucesso() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(visitanteRepository.findByCpf("00000000000")).thenReturn(Optional.of(visitante));
        when(enderecoService.cadastrarEndereco(endereco)).thenReturn(endereco);

        empresaService.cadastro(empresa);

        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    public void testAtivarEmpresa() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj(cnpj)).thenReturn((empresa));

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO(cnpj, null);

        empresaService.ativar(empresaDTO);

        assertTrue(empresa.isAtivo(), "Empresa deveria estar ativa");
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    public void testDesativarEmpresa() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj(cnpj)).thenReturn(empresa);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO(cnpj, null);

        empresaService.desativar(empresaDTO);

        assertFalse(empresa.isAtivo(), "Empresa deveria estar inativa");
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    public void testAtualizarEmpresa() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj((cnpj))).thenReturn(empresa);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO(cnpj, "Nome Empresa Atualizado");

        empresaService.atualizar(empresaDTO);

        assertEquals("Nome Empresa Atualizado", empresa.getNomeFantasia());
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    public void testObterEmpresa() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj((cnpj))).thenReturn(empresa);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO(cnpj, null);

        Empresa empresaObtida = empresaService.obter(empresaDTO);

        assertEquals(empresa, empresaObtida);
    }

    @Test
    public void testListarEmpresas() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        Page<Empresa> pagina = new PageImpl<>(Collections.singletonList(empresa));
        when(empresaRepository.findAll(any(Pageable.class))).thenReturn(pagina);

        Page<Empresa> resultado = empresaService.listarEmpresas(PageRequest.of(0, 10));

        assertEquals(1, resultado.getContent().size());
    }

    @Test
    public void testListarEmpresasAtivasComDuasEmpresas() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa1 = criarEmpresaAtiva(visitante, endereco);
        Empresa empresa2 = criarEmpresaAtiva(visitante, endereco);

        List<Empresa> empresasAtivas = List.of(empresa1, empresa2);
        Page<Empresa> pagina = new PageImpl<>(empresasAtivas);

        when(empresaRepository.findByAtivoTrue(any(Pageable.class))).thenReturn(pagina);

        Page<Empresa> resultado = empresaService.listarEmpresasAtivas(PageRequest.of(0, 10));

        assertEquals(2, resultado.getContent().size());
        assertTrue(resultado.getContent().get(0).isAtivo());
        assertTrue(resultado.getContent().get(1).isAtivo());
    }

    @Test
    public void testListarEmpresasInativas() {

        Endereco endereco = criarEndereco();

        Visitante visitante = criarVisitante();

        Empresa empresa1 = criarEmpresaInativa(visitante, endereco);
        Empresa empresa2 = criarEmpresaInativa(visitante, endereco);

        List<Empresa> empresasAtivas = List.of(empresa1, empresa2);
        Page<Empresa> pagina = new PageImpl<>(empresasAtivas);

        when(empresaRepository.findByAtivoFalse(any(Pageable.class))).thenReturn(pagina);

        Page<Empresa> resultado = empresaService.listarEmpresasInativas(PageRequest.of(0, 10));

        assertEquals(2, resultado.getContent().size());
        assertFalse(resultado.getContent().get(0).isAtivo());
        assertFalse(resultado.getContent().get(1).isAtivo());
    }

    // Teste com falhas (Exception)

    @Test
    public void deveLancarExcecaoQuandoNomeFantasiaExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByNomeFantasia(empresa.getNomeFantasia())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.of(visitante));

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoRazaoSocialExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByRazaoSocial(empresa.getRazaoSocial())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.of(visitante));

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoCnpjExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj(empresa.getCnpj())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.of(visitante));

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoEmailExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByEmail(empresa.getEmail())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.of(visitante));

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoTelefoneExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByTelefone(empresa.getTelefone())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.of(visitante));

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoVisitanteNaoExistir() {
        Endereco endereco = criarEndereco();
        Visitante visitante = criarVisitante();
        Empresa empresa = criarEmpresaAtiva(visitante, endereco);

        when(empresaRepository.findByCnpj(empresa.getCnpj())).thenReturn(empresa);
        when(visitanteRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(CadastroEmpresaException.class, () -> empresaService.cadastro(empresa));
    }

    @Test
    public void deveLancarExcecaoQuandoCnpjNaoExistirAoAtivarEmpresa() {
        when(empresaRepository.findByCnpj(anyString())).thenReturn(null);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO("12345678000100", null);

        assertThrows(AtualizarEmpresaException.class, () -> empresaService.ativar(empresaDTO));
    }

    @Test
    public void deveLancarExcecaoQuandoCnpjNaoExistirAoDesativarEmpresa() {
        when(empresaRepository.findByCnpj(anyString())).thenReturn(null);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO("12345678000100", null);

        assertThrows(AtualizarEmpresaException.class, () -> empresaService.desativar(empresaDTO));
    }

    @Test
    public void deveLancarExcecaoQuandoCnpjNaoExistirAoAtualizarEmpresa() {
        when(empresaRepository.findByCnpj(anyString())).thenReturn(null);

        AtualizarEmpresaDTO empresaDTO = new AtualizarEmpresaDTO("12345678000100", null);

        assertThrows(AtualizarEmpresaException.class, () -> empresaService.atualizar(empresaDTO));
    }
}
