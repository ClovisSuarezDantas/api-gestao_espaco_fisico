package api.gef.repository;

import api.gef.entity.Espaco;
import api.gef.entity.Solicitacao;
import api.gef.entity.Usuario;
import api.gef.enums.SolicitacaoStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    Optional<Solicitacao> findSolicitacaoBySolicitanteAndEspacoAndStatus(Usuario usuario, Espaco espaco, SolicitacaoStatusEnum status);
    Optional<List<Solicitacao>> findSolicitacaosByAvaliador(Usuario avaliador);
    Optional<List<Solicitacao>> findSolicitacaosBySolicitante(Usuario solicitante);
}
