package br.com.sistema.coworking.Entity;

import java.time.LocalDateTime;

import br.com.sistema.coworking.Enum.StatusReserva;
import br.com.sistema.coworking.Enum.TipoReserva;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoReserva tipo;
    @NotNull
    @ManyToOne(targetEntity = Sala.class)
    private Sala sala;
    @NotNull
    @ManyToOne(targetEntity = Estacao.class)
    private Estacao estacao;
    @NotNull
    @ManyToOne(targetEntity = Visitante.class)
    private Visitante visitante;
    @NotNull
    @ManyToOne(targetEntity = Empresa.class)
    private Empresa empresa;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @Enumerated(EnumType.STRING)
    private StatusReserva status;
    private boolean confirmada;
    @NotBlank
    private String observacao;
}
