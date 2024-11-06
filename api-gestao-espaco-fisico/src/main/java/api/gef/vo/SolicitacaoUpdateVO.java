package api.gef.vo;

import api.gef.enums.SolicitacaoStatusEnum;

public record SolicitacaoUpdateVO(
        SolicitacaoStatusEnum status
) {
}
