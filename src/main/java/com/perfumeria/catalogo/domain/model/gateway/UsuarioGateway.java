package com.perfumeria.catalogo.domain.model.gateway;

import com.perfumeria.catalogo.domain.model.UserInfo;

public interface UsuarioGateway {

    UserInfo obtenerUsuario(Long usuarioId);
}
