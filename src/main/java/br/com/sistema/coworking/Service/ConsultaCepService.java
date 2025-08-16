package br.com.sistema.coworking.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sistema.coworking.DTO.Endereco.EnderecoDTO;

@Service
public class ConsultaCepService {

    public EnderecoDTO consultarCep(String cep) {
        try {
            HttpClient http = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), EnderecoDTO.class);
            } else {
                throw new Exception("CEP não encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}