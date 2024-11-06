package api.gef.service;

import api.gef.entity.Espaco;
import api.gef.entity.Solicitacao;
import api.gef.entity.Usuario;
import api.gef.enums.*;
import api.gef.exception.EntityNotFoundException;
import api.gef.exception.EntityRejectedException;
import api.gef.helper.UsuarioHelper;
import api.gef.repository.EspacoRepository;
import api.gef.repository.SolicitacaoRepository;
import api.gef.repository.UsuarioRepository;
import api.gef.vo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EspacoService {
    private final EspacoRepository espacoRepository;
    private final UsuarioService usuarioService;
    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<EspacoFisicoVO> verEspacos() {
        return espacoRepository.findAll().stream().map(this::toResponse).toList();
    }

    private EspacoFisicoVO toResponse(Espaco espaco) {
        return EspacoFisicoVO.builder()
                .id(espaco.getId())
                .nome(espaco.getNome())
                .tipo(espaco.getEspacoTipo())
                .recursosDisponiveis(espaco.getRecursosDisponiveis())
                .localizacao(espaco.getLocalizacao())
                .capacidade(espaco.getCapacidade())
                .disponibilidade(espaco.getDisponibilidade())
                .build();
    }

    @Transactional
    public SolicitacaoResponseVO modificarStatusSolicitacao(Long solicitacaoId, SolicitacaoUpdateVO vo) {
        usuarioService.usuarioIsNotTeacher();

        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.SOLICITACAO_NAO_ENCONTRADA));

        if (solicitacao.getStatus() == SolicitacaoStatusEnum.AGUARDANDO) {
            throw new EntityRejectedException(ExceptionEnum.SOLICITACAO_NAO_ENVIADA_PARA_ANALISE);
        }

        solicitacao.setStatus(vo.status());
        Espaco espaco = solicitacao.getEspaco();

        if (vo.status() == SolicitacaoStatusEnum.FINALIZADA) {
            espaco.setDisponibilidade(DisponibilidadeEnum.DISPONIVEL);
        } else {
            espaco.setDisponibilidade(DisponibilidadeEnum.RESERVADO);
        }

        espacoRepository.save(espaco);
        espacoRepository.flush();
        solicitacaoRepository.save(solicitacao);

        return toResponse(solicitacao);
    }

    public List<SolicitacaoResponseVO> verHistorico() {
        return verSolicitacoes().stream().filter(
                solicitacao ->
                        solicitacao.status() == SolicitacaoStatusEnum.REPROVADA ||
                        solicitacao.status() == SolicitacaoStatusEnum.APROVADA ||
                        solicitacao.status() == SolicitacaoStatusEnum.FINALIZADA).toList();
    }

    public List<SolicitacaoResponseVO> verSolicitacoesProprias() {
            var usuario = UsuarioHelper.getAutheticatedUsuario();

            return solicitacaoRepository.findSolicitacaosBySolicitante(usuario).orElse(List.of()).stream()
                    .map(this::toResponse).toList();
    }

    public List<SolicitacaoResponseVO> verSolicitacoes() {
        usuarioService.usuarioIsNotTeacher();

        var usuario = UsuarioHelper.getAutheticatedUsuario();

        List<Solicitacao> solicitacoes = solicitacaoRepository.findSolicitacaosByAvaliador(usuario)
                .orElse(List.of());

        return solicitacoes.stream()
                .map(this::toResponse).toList();
    }

    @Transactional
    public void enviarSolicitacao(Long espacoId) {
        var espaco = findEspacoById(espacoId);
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        var solicitacao = solicitacaoRepository.findSolicitacaoBySolicitanteAndEspacoAndStatus(usuario, espaco, SolicitacaoStatusEnum.AGUARDANDO)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.SOLICITACAO_NAO_ENCONTRADA));

        var gestores = usuarioRepository.findUsuariosByUsuarioTipo(UsuarioTipoEnum.GESTOR).orElse(List.of());

        if (gestores.isEmpty()) {
            throw new EntityNotFoundException(ExceptionEnum.NAO_HA_GESTORES);
        }

        int ridx = new Random().nextInt(gestores.size());
        Usuario avaliador = gestores.get(ridx);

        solicitacao.setAvaliador(avaliador);
        solicitacao.setStatus(SolicitacaoStatusEnum.EM_ANALISE);

        solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public void deletarSolicitacao(Long espacoId) {
        var espaco = findEspacoById(espacoId);
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        var solicitacao = solicitacaoRepository.findSolicitacaoBySolicitanteAndEspacoAndStatus(usuario, espaco, SolicitacaoStatusEnum.AGUARDANDO)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.SOLICITACAO_NAO_ENCONTRADA));

        solicitacaoRepository.deleteById(solicitacao.getId());
    }

    @Transactional
    public void alterarSolicitacaoEspaco(EspacoSolicitacaoVO vo) {
        var espaco = findEspacoById(vo.espacoId());
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        var solicitacao = solicitacaoRepository.findSolicitacaoBySolicitanteAndEspacoAndStatus(usuario, espaco, SolicitacaoStatusEnum.AGUARDANDO)
               .orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.SOLICITACAO_NAO_ENCONTRADA));

        solicitacao.setNecessidades(vo.necessidades());
        solicitacao.setData_reserva(vo.data());

        solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public void solicitarEspaco(EspacoSolicitacaoVO vo) {
        var espaco = findEspacoById(vo.espacoId());
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        if (espaco.getDisponibilidade() == DisponibilidadeEnum.RESERVADO) {
            throw new EntityRejectedException(ExceptionEnum.ESPACO_JA_RESERVADO);
        }

        Optional<Solicitacao> solicitacao =
                solicitacaoRepository.findSolicitacaoBySolicitanteAndEspacoAndStatus(usuario, espaco, SolicitacaoStatusEnum.AGUARDANDO);

        if (solicitacao.isPresent()) {
            throw new EntityRejectedException(ExceptionEnum.SOLICITACAO_JA_ABERTA);
        }

        var novaSolicitacao = Solicitacao.builder()
                .id(0L)
                .espaco(espaco)
                .avaliador(null)
                .status(SolicitacaoStatusEnum.AGUARDANDO)
                .solicitante(usuario)
                .necessidades(vo.necessidades())
                .data_reserva(vo.data())
                .build();

        solicitacaoRepository.save(novaSolicitacao);
    }

    public Espaco findEspacoById(Long id) {
        return espacoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.ESPACO_NAO_ENCONTRADO));
    }

    @Transactional
    public void alterarDisponibilidade(Long espacoId) {
        usuarioService.usuarioIsAdmin();

        var espaco = findEspacoById(espacoId);

        espaco.setDisponibilidade(espaco.getDisponibilidade() == DisponibilidadeEnum.DISPONIVEL ?
                DisponibilidadeEnum.RESERVADO : DisponibilidadeEnum.DISPONIVEL);

        espacoRepository.save(espaco);
    }

    @Transactional
    public void modificarEspaco(Long espacoId, EspacoCriarVO vo) {
        usuarioService.usuarioIsAdmin();

        var espaco = findEspacoById(espacoId);

        espaco.setNome(vo.nome());
        espaco.setEspacoTipo(vo.tipo());
        espaco.setCapacidade(vo.capacidade());
        espaco.setLocalizacao(vo.localizacao());
        espaco.setRecursosDisponiveis(vo.recursosDisponiveis());

        espacoRepository.save(espaco);
    }

    @Transactional
    public void criarEspaco(EspacoCriarVO vo) {
        usuarioService.usuarioIsAdmin();

        var espaco = Espaco.builder()
                .id(0L)
                .nome(vo.nome())
                .espacoTipo(vo.tipo())
                .capacidade(vo.capacidade())
                .localizacao(vo.localizacao())
                .recursosDisponiveis(vo.recursosDisponiveis())
                .disponibilidade(DisponibilidadeEnum.DISPONIVEL)
                .build();

        espacoRepository.save(espaco);
    }

    public SolicitacaoResponseVO toResponse(Solicitacao s) {
        return SolicitacaoResponseVO.builder()
                        .id(s.getId())
                        .necessidades(s.getNecessidades())
                        .espacoNome(s.getEspaco().getNome())
                        .dataReserva(s.getData_reserva())
                        .status(s.getStatus())
                        .usuarioEmail(s.getSolicitante().getEmail())
                        .build();
    }
}
