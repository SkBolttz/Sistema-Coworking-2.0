package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;

import br.com.sistema.coworking.Enum.AprovacaoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString

@Entity
@Table(name = "tb_empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(unique = true)
    private String nomeFantasia;
    @NotBlank
    @Column(unique = true)
    private String razaoSocial;
    @NotBlank
    @Column(unique = true)
    private String cnpj;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @Column(unique = true)
    private String telefone;
    @OneToOne(targetEntity = Visitante.class)
    private Visitante responsavel;
    @ManyToOne
    @JoinColumn(name = "ramo_id", nullable = false)
    private RamoEmpresarial ramo;
    @NotNull
    @ManyToOne(targetEntity = Endereco.class)
    private Endereco endereco;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    @Enumerated(EnumType.STRING)
    private AprovacaoStatus status;
    private boolean ativo;

    public Empresa(long id, @NotBlank String nomeFantasia, @NotBlank String razaoSocial, @NotBlank String cnpj,
            @NotBlank @Email String email, @NotBlank String telefone, Visitante responsavel,
            @NotBlank RamoEmpresarial ramo,
            @NotNull Endereco endereco, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
            AprovacaoStatus status, boolean ativo) {
        this.id = id;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.responsavel = responsavel;
        this.ramo = ramo;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.status = status;
        this.ativo = ativo;
    }

}
