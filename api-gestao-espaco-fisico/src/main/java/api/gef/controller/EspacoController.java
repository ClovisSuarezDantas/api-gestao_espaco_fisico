package api.gef.controller;

import api.gef.annotations.Auditar;
import api.gef.service.EspacoService;
import api.gef.vo.EspacoCriarVO;
import api.gef.vo.EspacoFisicoVO;
import api.gef.vo.EspacoSolicitacaoVO;
import api.gef.vo.SolicitacaoResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espaco")
@RequiredArgsConstructor
public class EspacoController {
    private final EspacoService espacoService;

    @Auditar(action = "visualizou-espacos-fiscos")
    @GetMapping
    public ResponseEntity<List<EspacoFisicoVO>> verEspacos() {
        return ResponseEntity.ok(espacoService.verEspacos());
    }

    @Auditar(action = "visualizou-solicitacoes-proprias")
    @GetMapping("/solicitacoes")
    public ResponseEntity<List<SolicitacaoResponseVO>> verSolicitacoesProprias() {
        return ResponseEntity.ok(espacoService.verSolicitacoesProprias());
    }

    @Auditar(action = "solicitou-espaco-fisico")
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarEspaco(@RequestBody EspacoSolicitacaoVO espacoSolicitacaoVO) {
        espacoService.solicitarEspaco(espacoSolicitacaoVO);
        return ResponseEntity.ok().build();
    }

    @Auditar(action = "alterou-solicitacao-espaco-fisico")
    @PutMapping("/alterar-solicitacao")
    public ResponseEntity<?> alterarSolicitacaoEspaco(@RequestBody EspacoSolicitacaoVO espacoSolicitacaoVO) {
        espacoService.alterarSolicitacaoEspaco(espacoSolicitacaoVO);
        return ResponseEntity.accepted().build();
    }

    @Auditar(action = "deletou-solicitacao-espaco-fisico")
    @DeleteMapping("/deletar-solicitacao/{espacoId}")
    public ResponseEntity<?> deletarSolicitacaoEspaco(@PathVariable("espacoId") Long espacoId) {
        espacoService.deletarSolicitacao(espacoId);
        return ResponseEntity.noContent().build();
    }

    @Auditar(action = "enviar-solicitacao-espaco-fisico")
    @PutMapping("/enviar-solicitacao/{espacoId}")
    public ResponseEntity<?> enviarSolicitacao(@PathVariable("espacoId") Long espacoId) {
        espacoService.enviarSolicitacao(espacoId);
        return ResponseEntity.noContent().build();
    }

    @Auditar(action = "criou-espaco-fisico")
    @PostMapping("/criar")
    public ResponseEntity<?> criarEspaco(@RequestBody EspacoCriarVO espacoCriarVO) {
        espacoService.criarEspaco(espacoCriarVO);
        return ResponseEntity.ok().build();
    }

    @Auditar(action = "modificou-espaco-fisico")
    @PutMapping("/modificar-espaco/{espacoId}")
    public ResponseEntity<?> modificarEspaco(@PathVariable("espacoId") Long espacoId, @RequestBody EspacoCriarVO espacoCriarVO) {
        espacoService.modificarEspaco(espacoId, espacoCriarVO);
        return ResponseEntity.ok().build();
    }

    @Auditar(action = "alterou-disponibilidade")
    @PutMapping("/alterar-disponibilidade/{espacoId}")
    public ResponseEntity<?> alterarDisponbilidade(@PathVariable("espacoId") Long espacoId) {
        espacoService.alterarDisponibilidade(espacoId);
        return ResponseEntity.ok().build();
    }
}
