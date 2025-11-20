package com.perfumeria.catalogo.infrastructure.driver_adapters.rest_client;

import com.perfumeria.catalogo.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


import java.util.Map;

@RequiredArgsConstructor
@Component
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final RestTemplate restTemplate;

    // ✅ Leer URL desde variables de entorno
    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Override
    public boolean usuarioExiste(Long usuarioId) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    authServiceUrl + "/api/perfumeria/usuario/" + usuarioId,  // ✅ Usar variable
                    Map.class
            );

            // Si el cuerpo tiene la clave "mensaje" con texto "Usuario no encontrado..."
            Object mensaje = response.getBody() != null ? response.getBody().get("mensaje") : null;

            if (mensaje != null && mensaje.toString().contains("Usuario no encontrado")) {
                return false;
            }

            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception errorMensaje) {
            throw new RuntimeException("Error al consultar el servicio de autenticación", errorMensaje);
        }
    }
}
