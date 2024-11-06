package api.gef.vo;

import api.gef.enums.DisponibilidadeEnum;
import api.gef.enums.EspacoTipoEnum;
import lombok.Builder;

@Builder
public record EspacoFisicoVO(
        Long id,
        String nome,
        Integer capacidade,
        String recursosDisponiveis,
        String localizacao,
        DisponibilidadeEnum disponibilidade,
        EspacoTipoEnum tipo
) {

}
