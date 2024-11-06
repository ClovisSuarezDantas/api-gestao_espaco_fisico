package api.gef.aspect;

import api.gef.annotations.Auditar;
import api.gef.entity.Auditoria;
import api.gef.repository.AuditoriaRepository;
import api.gef.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditoriaAspect {
    private final UsuarioService usuarioService;
    private final AuditoriaRepository auditoriaRepository;

    @SneakyThrows
    @Around("execution(public * *(..)) && @annotation(api.gef.annotations.Auditar)")
    public Object processPointCut(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Auditar auditAction = method.getAnnotation(Auditar.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        audit(authentication, auditAction);

        return proceedingJoinPoint.proceed();
    }

    private void audit(Authentication authentication, Auditar auditAction) {
        var usuario = usuarioService.getAuthenticatedUsuario(authentication);
        var auditoria = Auditoria.builder()
                .id(0L)
                .acao(auditAction.action())
                .email(usuario.getEmail())
                .dataRegistro(LocalDateTime.now())
                .build();
        auditoriaRepository.save(auditoria);
    }
}
