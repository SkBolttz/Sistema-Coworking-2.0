package br.com.sistema.coworking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.sistema.coworking.Entity.Endereco;
import br.com.sistema.coworking.Repository.EnderecoRepository;
import br.com.sistema.coworking.Service.EnderecoService;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco criarEndereco() {
        Endereco endereco = new Endereco(1L, "rua", "89031576", "cidade", "estado", "pais", "37");

        return endereco;
    }

    @Test
    public void testCadastrarEndereco() {

        Endereco endereco = criarEndereco();

        when(enderecoRepository.findByLogradouroAndNumeroAndCep(endereco.getLogradouro(), endereco.getNumero(),
                endereco.getCep())).thenReturn(null);

        enderecoService.cadastrarEndereco(endereco);

        verify(enderecoRepository, times(1)).save(endereco);
        assertEquals(endereco, enderecoService.cadastrarEndereco(endereco));
    }

    @Test
    public void testNaoSalvarEnderecoQuandoJaEstiverCadastrado() {

        Endereco endereco = criarEndereco();

        when(enderecoRepository.findByLogradouroAndNumeroAndCep(endereco.getLogradouro(), endereco.getNumero(),
                endereco.getCep())).thenReturn(endereco);

        assertEquals(endereco, enderecoService.cadastrarEndereco(endereco));
    }
}
