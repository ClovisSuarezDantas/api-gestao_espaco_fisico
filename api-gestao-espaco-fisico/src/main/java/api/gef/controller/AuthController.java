package api.gef.controller;

import api.gef.security.service.AuthService;
import api.gef.service.UsuarioService;
import api.gef.vo.UsuarioCredenciaisVO;
import api.gef.vo.UsuarioDetailsVO;
import api.gef.vo.UsuarioRegisterVO;
import api.gef.vo.UsuarioTokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UsuarioRegisterVO usuarioRegisterVO) {
        usuarioService.register(usuarioRegisterVO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenVO> login(@RequestBody UsuarioCredenciaisVO usuarioCredenciaisVO) {
        return ResponseEntity.ok(authService.authenticate(usuarioCredenciaisVO));
    }

    @GetMapping("/current-usuario")
    public ResponseEntity<UsuarioDetailsVO> details() {
        return ResponseEntity.ok(usuarioService.getCurrentUsuario());
    }
}
