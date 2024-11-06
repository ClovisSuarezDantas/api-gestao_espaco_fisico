package api.gef.security.service;

import api.gef.service.UsuarioService;
import api.gef.vo.UsuarioCredenciaisVO;
import api.gef.vo.UsuarioTokenVO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private TokenService tokenService;
    private UsuarioService usuarioService;
    private AuthenticationManager authenticationManager;

    @SneakyThrows
    public UsuarioTokenVO authenticate(UsuarioCredenciaisVO usuarioCredenciaisVO) {
        var usuario = usuarioService.findUsuarioByEmail(usuarioCredenciaisVO.email());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usuarioCredenciaisVO.email(), usuarioCredenciaisVO.senha()));
        return new UsuarioTokenVO(tokenService.generateToken(usuario));
    }
}