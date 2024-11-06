package api.gef.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuditoriaVO(
        String usuario,
        String acao,
        LocalDateTime dataRegistro
) {
}
