package api.gef.service;

import api.gef.entity.Usuario;
import api.gef.enums.ExceptionEnum;
import api.gef.enums.UsuarioTipoEnum;
import api.gef.exception.EntityAlreadyExistsException;
import api.gef.exception.EntityForbiddenAccessException;
import api.gef.exception.EntityNotFoundException;
import api.gef.helper.UsuarioHelper;
import api.gef.repository.UsuarioRepository;
import api.gef.vo.UsuarioDetailsVO;
import api.gef.vo.UsuarioRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UsuarioRegisterVO vo) {
        if (usuarioRepository.findUsuarioByEmail(vo.email()).isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionEnum.USUARIO_JA_EXISTE);
        }

        var usuario = Usuario.builder()
                .id(0L)
                .nome(vo.nome())
                .senha(passwordEncoder.encode(vo.senha()))
                .email(vo.email())
                .usuarioTipo((vo.tipo()))
                .build();

        usuarioRepository.save(usuario);
    }

    public UsuarioDetailsVO getCurrentUsuario() {
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        return UsuarioDetailsVO.builder()
                .dataRegistro(usuario.getDataRegistro())
                .email(usuario.getEmail())
                .id(usuario.getId())
                .nome(usuario.getNome())
                .tipo(usuario.getUsuarioTipo())
                .build();
    }

    public void usuarioIsAdmin() {
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        if (usuario.getUsuarioTipo() != UsuarioTipoEnum.ADMINISTRADOR) {
            throw new EntityForbiddenAccessException(ExceptionEnum.USUARIO_SEM_PERMISSAO);
        }
    }

    public void usuarioIsNotTeacher() {
        var usuario = UsuarioHelper.getAutheticatedUsuario();

        if (usuario.getUsuarioTipo() == UsuarioTipoEnum.PROFESSOR) {
            throw new EntityForbiddenAccessException(ExceptionEnum.USUARIO_SEM_PERMISSAO);
        }
    }

    public Usuario getAuthenticatedUsuario(Authentication authentication) {
        return usuarioRepository.findById(((Usuario)(authentication.getPrincipal())).getId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.USUARIO_NAO_ENCONTRADO));
    }

    public Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email).orElseThrow(() -> new EntityNotFoundException(ExceptionEnum.USUARIO_NAO_ENCONTRADO));
    }
}
