package api.gef.vo;

import api.gef.enums.UsuarioTipoEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioDetailsVO(
        Long id,
        String nome,
        String email,
        LocalDate dataRegistro,
        UsuarioTipoEnum tipo
) {
}
