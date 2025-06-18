package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;

import br.com.sistema.coworking.Enum.AprovacaoStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "tb_empresa")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String nomeFantasia;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String cnpj;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String telefone;
    @NotNull
    @OneToOne(targetEntity = Visitante.class)
    private Visitante responsavel;
    @NotBlank
    private String ramo;
    @NotNull
    @ManyToOne(targetEntity = Endereco.class)
    private Endereco endereco;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    @NotNull
    private AprovacaoStatus status;

}
