package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "tb_estacao")
public class Estacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String identificacao;
    @NotBlank
    private String descricao;
    private boolean disponivel;
    @NotNull
    private boolean monitor;
    @NotNull
    private boolean tecladoMouse;
    @NotNull
    private boolean cadeiraErgonomica;
    @NotNull
    @OneToOne
    private Sala sala;
    @NotBlank
    private String fotoUrl;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataManutencao;
}
