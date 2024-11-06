package api.gef.entity;

import api.gef.enums.UsuarioTipoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @CreationTimestamp
    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "usuario_tipo", nullable = false)
    private UsuarioTipoEnum usuarioTipo;

    @OneToMany(mappedBy = "solicitante")
    @JsonIgnore
    @ToString.Exclude
    private List<Solicitacao> solicitacoes;

    @OneToMany(mappedBy = "avaliador")
    @JsonIgnore
    @ToString.Exclude
    private List<Solicitacao> avaliacoes;
}
