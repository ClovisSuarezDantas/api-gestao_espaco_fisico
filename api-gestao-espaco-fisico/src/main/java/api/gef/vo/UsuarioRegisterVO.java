package api.gef.vo;

import api.gef.enums.UsuarioTipoEnum;

public record UsuarioRegisterVO(String nome, String email, String senha, UsuarioTipoEnum tipo) {
}
