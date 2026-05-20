package pe.gob.vuce.cp2.bs.domain.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ComprobanteModel {
      private String textSearch;
      private Integer entidadId;
      private Integer actividadId;
      private Integer documentoId;
      private Integer escalaId;
      private String fechaVigencia;
      private String rucAgente;
      private Integer actividadEntidadPuertoId;
      private String idComponente;
      private Integer codComponente;
      private Integer procedimientoId;
      private Integer procedimientoVersion;
      private Integer procedimientoTasaVersion;
      private Integer secuencia;
      private BigDecimal monto;
      private String etiqueta;
      private String descripcion;
      private String codigoMoneda;
      private String monedaDescripcion;
      private String monedaSigno;
}
