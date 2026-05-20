package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.DueModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.model.ParametrosModel;
import pe.gob.vuce.cp2.bs.domain.port.out.RepositoryPort;
import pe.gob.vuce.cp2.bs.infrastructure.mapper.ReglasMapper;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.ActividadEntidadPuertoRepository;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.ConvoyEscalaRepository;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.EntidadRepository;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.EscalaRepository;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.MotivoEscalaRepository;
import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository.OrdenPagoRepository;


@AllArgsConstructor
@Component
public class RepositoryAdapter implements RepositoryPort {
  
    private final ReglasMapper mapper;
    private final EscalaRepository escalaRepository;
    private final ActividadEntidadPuertoRepository actividadEntidadPuertoRepository;
    private final MotivoEscalaRepository motivoEscalaRepository;
    private final ConvoyEscalaRepository convoyEscalaRepository;
    private final EntidadRepository entidadRepository;
    private final OrdenPagoRepository ordenPagoRepository;

    @Override
    public OperacionModel obtenerPuerto(OperacionModel model) {
        ParametrosModel parametrosModel = model.getParametrosModel();
        DueModel dueModel = model.getDueModel();
        return escalaRepository.findById(parametrosModel.getEscalaId())
            .map(entity -> {
                dueModel.setCodigoPuerto(entity.getPuertoEscalaId());
                return model;
            })
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró el registro con escalaId: " + parametrosModel.getEscalaId()
            ));
    }    

    @Override
    public OperacionModel buscarActividadEntidadPuerto(OperacionModel model) {
        ParametrosModel parametrosModel = model.getParametrosModel();
        DueModel dueModel = model.getDueModel();
        return actividadEntidadPuertoRepository
                .findByActividadIdAndCodPuertoNacional(
                        parametrosModel.getActividadId(),
                        dueModel.getCodigoPuerto()
                )
                .map(entity -> {
                    dueModel.setEntidadId(entity.getEntidadId());
                    dueModel.setReglaNegocio(entity.getCodReglaNegocio());
                    return model;
                })
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el registro con actividadId: "
                                + parametrosModel.getActividadId()
                                + " y codPuertoNacional: "
                                + dueModel.getCodigoPuerto()
                ));
    }

    @Override
    public OperacionModel buscarMotivoEscala(OperacionModel model) {
        ParametrosModel parametrosModel = model.getParametrosModel();
        DueModel dueModel = model.getDueModel();

        return motivoEscalaRepository.findByEscalaIdAndMotivoId(
                parametrosModel.getEscalaId(),
                3
        )
        .map(entity -> {
            dueModel.setEsArrriboForzoso(true);
            return model;
        })
        .orElseGet(() -> {
            dueModel.setEsArrriboForzoso(false);
            return model;
        });
    }

    @Override
    public OperacionModel buscarConvoy(OperacionModel model) {
        ParametrosModel parametrosModel = model.getParametrosModel();
        DueModel dueModel = model.getDueModel();
        return convoyEscalaRepository.findByEscalaIdAndPrincipal(
                parametrosModel.getEscalaId(),
                true
        )
        .map(entity -> {
            dueModel.setEsNavelPrincipal(true);
            return model;
        })
        .orElseGet(() -> {
            dueModel.setEsNavelPrincipal(false);
            return model;
        });
    }

    @Override
    public OperacionModel buscarEntidad(OperacionModel model) {
        DueModel dueModel = model.getDueModel();
        return entidadRepository.findById(dueModel.getEntidadId())
            .map(entity -> {
                dueModel.setCodEntidadGp(entity.getCodEntidadGp());
                return model;
            })
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró el registro con entidadId: " + dueModel.getEntidadId()
            ));
    }

    @Override
    public OperacionModel buscarOrdenPago(OperacionModel model) {
        ParametrosModel parametrosModel = model.getParametrosModel();
        DueModel dueModel = model.getDueModel();
        return ordenPagoRepository.findByEntidadIdAndDocumentoIdAndEscalaIdAndEstadoOrdenPago(
                dueModel.getEntidadId(),
                parametrosModel.getDocumentoId(),
                parametrosModel.getEscalaId(),
                "S"
        ).map(entity -> {
            dueModel.setComprobanteRegistrado(true);
            return model;
        })
        .orElseGet(() -> {
            dueModel.setComprobanteRegistrado(false);
            return model;
        });
    }

   
}
