package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.sistema.coworking.Enum.TipoVisitante;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_visitante")
public class Visitante implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Size(min = 10, max = 40, message = "Nome de usuario deve ter entre 10 e 40 caracteres")
    private String nomeCompleto;
    @NotBlank
    @Column(unique = true)
    @Size(min = 11, max = 11, message = "CPF deve ter 11 digitos")
    private String cpf;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 digitos")
    private String telefone;
    @ManyToOne(targetEntity = Empresa.class)
    private Empresa empresa;
    @NotBlank
    private String fotoDocumentoUrl;
    private String observacao;
    private LocalDateTime dataCadastro;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoVisitante tipo;
    private boolean ativo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipo.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getCpf();
    }

    @Override
    public String toString() {
        return "Visitante{" +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", empresa=" + empresa +
                ", observacao='" + observacao + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", tipo=" + tipo +
                ", ativo=" + ativo +
                '}';
    }
}

// Todos os usuarios devem realizar cadastro / login, mas sera que empresa
// precisa tambem?
// Como a empresa se cadastraria? Atraves de um form? Se sim, quem iria aprovar?
// Teria que ter funcionario?

// Posso fazer com que somente os visitante realizem cadastro / login, Empresas
// para sua cadastracao deve ser enviado um form
// Apos aprovacao de cadastro, as empresas poderiam cadastrar seus funcionarios

// Sera que e uma boa ideia? Ou nao?

// Neste caso, se levar em consideracao o Login / Registro, deve de haver senhas
// para acesso