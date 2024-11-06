package api.gef.entity;

import api.gef.enums.DisponibilidadeEnum;
import api.gef.enums.EspacoTipoEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "espaco")
@NoArgsConstructor
@AllArgsConstructor
public class Espaco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @Column(name = "recursos_disponiveis", nullable = false)
    private String recursosDisponiveis;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidade", nullable = false)
    private DisponibilidadeEnum disponibilidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_espaco", nullable = false)
    private EspacoTipoEnum espacoTipo;

    @OneToMany(mappedBy = "espaco", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "solicitacao_espaco_reference")
    private List<Solicitacao> solicitacoes;
}
