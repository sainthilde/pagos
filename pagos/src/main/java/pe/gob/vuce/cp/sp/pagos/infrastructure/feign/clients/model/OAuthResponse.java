package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * La clase OAuthResponse representa la respuesta de autenticación OAuth,
 * que contiene información sobre el token de acceso y otros detalles relacionados.
 * Utiliza anotaciones de Lombok para simplificar la generación de métodos de acceso
 * y modificación, y {@code @JsonProperty} para mapear los nombres de los atributos
 * a los campos JSON correspondientes.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase.</li>
 *   <li>{@code @JsonProperty}: Especifica el nombre exacto del campo en el JSON
 *       que se asignará a cada atributo, permitiendo el mapeo de nombres de
 *       atributos en el código a nombres de campos JSON que puedan diferir.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code accessToken}: Token de acceso emitido por el servidor OAuth,
 *       representado por el campo JSON {@code "access_token"}.</li>
 *   <li>{@code scope}: Ámbito de autorización asociado al token, representado
 *       por el campo JSON {@code "scope"}.</li>
 *   <li>{@code tokenType}: Tipo de token, por ejemplo, {@code "Bearer"},
 *       representado por el campo JSON {@code "token_type"}.</li>
 *   <li>{@code expiresIn}: Tiempo de expiración en segundos del token de acceso,
 *       representado por el campo JSON {@code "expires_in"}.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class OAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

}

