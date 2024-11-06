package api.gef.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    SOLICITACAO_NAO_ENVIADA_PARA_ANALISE(400, "Solicitação ainda não enviada para analise"),
    NAO_HA_GESTORES(404, "Não há gestores cadastrados"),
    SOLICITACAO_NAO_ENCONTRADA(404, "A solicitação de espaço não foi encontrada"),
    SOLICITACAO_JA_ABERTA(400, "Você já possui uma solicitação para esse espaço em analise"),
    ESPACO_NAO_ENCONTRADO(404, "Espaço não encontrado"),
    ESPACO_JA_RESERVADO(404, "O espaço ja foi reservado"),
    USUARIO_SEM_PERMISSAO(403, "Usuário sem permissão"),
    USUARIO_JA_EXISTE(422, "O usuário com o email informado já existe"),
    USUARIO_NAO_ENCONTRADO(404, "Usuário não encontrado");

    final String message;
    final Integer code;

    ExceptionEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
