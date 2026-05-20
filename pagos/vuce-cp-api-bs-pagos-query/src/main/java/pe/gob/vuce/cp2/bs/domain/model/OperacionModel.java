package pe.gob.vuce.cp2.bs.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OperacionModel {
    private MetaModel metaModel;
    private DataModel dataModel;
    
    private ParametrosModel parametrosModel;
    private GestorProcedimientoModel gestorProcedimientoModel;
  
}
