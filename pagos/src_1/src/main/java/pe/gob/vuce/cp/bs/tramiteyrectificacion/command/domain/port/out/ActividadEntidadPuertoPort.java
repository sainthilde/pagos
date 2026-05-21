package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;

public interface ActividadEntidadPuertoPort {
    ActividadEntidadPuertoModel findByActividadIdAndCodPuertoNacionalAndEstado(Integer actividadId,
            String codPuertoNacional, String estado);
}
