package api.gef.entity;

import api.gef.enums.SolicitacaoStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "solicitacao")
@NoArgsConstructor
@AllArgsConstructor
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario solicitante;

    @ManyToOne
    private Usuario avaliador;

    @ManyToOne
    private Espaco espaco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SolicitacaoStatusEnum status;

    @Column(name = "necessidades", nullable = false)
    private String necessidades;

    @Column(name = "data_reserva", nullable = false)
    private LocalDate data_reserva;
}
