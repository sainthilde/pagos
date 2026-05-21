package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * La clase OrdenPagoRequestDTO es un objeto de transferencia de datos (DTO) que
 * representa una solicitud para crear o procesar una orden de pago. Contiene
 * información relevante sobre la entidad, perfil, formato, monto, y otras
 * referencias necesarias para procesar una orden de pago en el sistema.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos
 *       de acceso (getters) y modificación (setters) para cada atributo en la clase.</li>
 *   <li>{@code @NoArgsConstructor} y {@code @AllArgsConstructor}: Generan un
 *       constructor sin argumentos y otro con todos los argumentos, respectivamente,
 *       para facilitar la creación de instancias de la clase.</li>
 *   <li>{@code @JsonInclude(JsonInclude.Include.NON_NULL)}: Excluye los atributos
 *       nulos de la representación JSON, optimizando el tamaño del objeto JSON
 *       generado y evitando el envío de valores nulos en las respuestas.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code entidadId}: Identificador de la entidad asociada a la orden de pago.</li>
 *   <li>{@code perfilId}: Identificador del perfil de usuario que realiza la solicitud.</li>
 *   <li>{@code formato}: Formato de la orden de pago.</li>
 *   <li>{@code desFormato}: Descripción del formato de la orden de pago.</li>
 *   <li>{@code tupa}: Código TUPA asociado a la orden de pago.</li>
 *   <li>{@code montoExacto}: Monto exacto en la orden de pago.</li>
 *   <li>{@code fechaVigencia}: Fecha de vigencia de la orden de pago.</li>
 *   <li>{@code codDocumento}: Código del documento asociado a la orden de pago.</li>
 *   <li>{@code nombreUsuario}: Nombre del usuario que realiza la solicitud.</li>
 *   <li>{@code nroDocumento}: Número de documento del usuario.</li>
 *   <li>{@code tipoCodigoReferencia}: Tipo de código de referencia para la orden de pago.</li>
 *   <li>{@code tipoReferencia1}, {@code codReferencia1}: Primer tipo y código de referencia.</li>
 *   <li>{@code tipoReferencia2}, {@code codReferencia2}: Segundo tipo y código de referencia.</li>
 *   <li>{@code componenteId}: Identificador del componente asociado a la orden de pago.</li>
 *   <li>{@code tipoOperador}: Tipo de operador, relevante para la validación o
 *       procesamiento de la orden de pago.</li>
 *   <li>{@code rucOperador}: RUC (Registro Único de Contribuyente) del operador
 *       que gestiona la orden de pago.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenPagoRequestDTO {
    private Integer entidadId;
    private Integer perfilId;
    private String formato;
    private String desFormato;
    private String tupa;
    private Double montoExacto;
    private String fechaVigencia;
    private String codDocumento;
    private String nombreUsuario;
    private String nroDocumento;
    private String tipoCodigoReferencia;
    private String tipoReferencia1;
    private String codReferencia1;
    private String tipoReferencia2;
    private String codReferencia2;
    private Integer componenteId;
    private String tipoOperador;
    private String rucOperador;
}

