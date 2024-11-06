package api.gef.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EspacoSolicitacaoVO(
        Long espacoId,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data,
        String necessidades) {
}
