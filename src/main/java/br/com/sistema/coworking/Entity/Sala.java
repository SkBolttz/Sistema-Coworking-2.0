package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;

import br.com.sistema.coworking.Enum.TipoSala;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private int quantidade;
    private boolean disponivel;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoSala tipo;
    @NotBlank
    private String fotoUrl;
    @NotBlank
    private String localizacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataManutencao;
    @OneToOne(mappedBy = "sala", orphanRemoval = true)
    private Estacao estacao;

    public Sala(long id, String nome, String descricao, int quantidade, boolean disponivel, TipoSala tipo,
            String fotoUrl,
            String localizacao, LocalDateTime dataCriacao, LocalDateTime dataManutencao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.tipo = tipo;
        this.fotoUrl = fotoUrl;
        this.localizacao = localizacao;
        this.dataCriacao = dataCriacao;
        this.dataManutencao = dataManutencao;
    }
}
