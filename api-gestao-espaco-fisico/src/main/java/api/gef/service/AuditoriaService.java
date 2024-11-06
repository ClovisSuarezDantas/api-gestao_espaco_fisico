package api.gef.service;

import api.gef.entity.Auditoria;
import api.gef.repository.AuditoriaRepository;
import api.gef.vo.AuditoriaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuditoriaService {
    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioService usuarioService;

    public List<AuditoriaVO> verAuditoria() {
        usuarioService.usuarioIsAdmin();
        return auditoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AuditoriaVO toResponse(Auditoria auditoria) {
        return AuditoriaVO.builder()
                .acao(auditoria.getAcao())
                .dataRegistro(auditoria.getDataRegistro())
                .usuario(auditoria.getEmail())
                .build();
    }
}
