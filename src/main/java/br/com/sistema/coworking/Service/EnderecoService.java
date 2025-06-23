package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.Entity.Endereco;
import br.com.sistema.coworking.Repository.EnderecoRepository;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco cadastrarEndereco(Endereco endereco) {

        Endereco enderecoExiste = enderecoRepository.findByLogradouroAndNumeroAndCep(endereco.getLogradouro(),
                endereco.getNumero(), endereco.getCep());

        if (enderecoExiste == null) {
            enderecoRepository.save(endereco);
        }

        return enderecoExiste;
    }
}
