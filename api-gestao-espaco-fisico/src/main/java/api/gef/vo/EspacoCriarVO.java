package api.gef.vo;

import api.gef.enums.EspacoTipoEnum;

public record EspacoCriarVO(String nome, Integer capacidade, String recursosDisponiveis, String localizacao, EspacoTipoEnum tipo) {
}
