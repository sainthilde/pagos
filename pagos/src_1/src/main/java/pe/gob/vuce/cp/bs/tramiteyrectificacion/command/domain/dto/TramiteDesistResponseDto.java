package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO que representa una solicitud de seguimiento de tramite,
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TramiteDesistResponseDto {
    Integer tramiteId;
    String fechaTramite;
    String  numeroSuce;
    Integer escalaId;
    Integer documentoId;
    Integer actividadEntidadPuertoId;
    String indicadorEs;
    String estadoTramite;

    List<String> ppCpbPayments;

    // Campos simples para errores
    private boolean exito;
    private String mensajeError;
    private List<Object> respuestasAnulacion;
}
