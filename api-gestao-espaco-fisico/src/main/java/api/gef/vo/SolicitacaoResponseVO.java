package api.gef.vo;

import api.gef.enums.SolicitacaoStatusEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SolicitacaoResponseVO(
        Long id,
        String usuarioEmail,
        String espacoNome,
        SolicitacaoStatusEnum status,
        String necessidades,
        LocalDate dataReserva) {
}
