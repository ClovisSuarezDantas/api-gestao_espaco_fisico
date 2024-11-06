package api.gef.controller;

import api.gef.annotations.Auditar;
import api.gef.service.AuditoriaService;
import api.gef.service.EspacoService;
import api.gef.vo.AuditoriaVO;
import api.gef.vo.SolicitacaoResponseVO;
import api.gef.vo.SolicitacaoUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerenciar")
@RequiredArgsConstructor
public class GerenciamentoController {
    private final EspacoService espacoService;
    private final AuditoriaService auditoriaService;

    @Auditar(action = "visualizou-solicitacoes")
    @GetMapping("/solicitacoes")
    public ResponseEntity<List<SolicitacaoResponseVO>> verSolicitacoes() {
        return ResponseEntity.ok(espacoService.verSolicitacoes());
    }

    @Auditar(action = "visualizou-proprio-historico")
    @GetMapping("/historico")
    public ResponseEntity<List<SolicitacaoResponseVO>> verHistorico() {
        return ResponseEntity.ok(espacoService.verHistorico());
    }

    @Auditar(action = "modificou-status-solicitacao")
    @PutMapping("/status-solicitacao/{solicitacaoId}")
    public ResponseEntity<SolicitacaoResponseVO> aprovarSolicitacao(@PathVariable("solicitacaoId") Long solicitacaoId, @RequestBody SolicitacaoUpdateVO solicitacaoUpdateVO) {
        return ResponseEntity.ok(espacoService.modificarStatusSolicitacao(solicitacaoId, solicitacaoUpdateVO));
    }

    @Auditar(action = "visualizou-auditoria")
    @GetMapping("/auditoria")
    public ResponseEntity<List<AuditoriaVO>> verAuditoria() {
        return ResponseEntity.ok(auditoriaService.verAuditoria());
    }
}
