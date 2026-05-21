package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.gob.vuce.cp.sp.pagos.application.service.ActividadEntidadService;
import pe.gob.vuce.cp.sp.pagos.application.usecase.ActividadEntidadUseCaseImpl;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ActividadEntidadRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository.JpaActividadEntidadRepositoryAdapter;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository.JpaOrdenPagoRepositoryAdapter;

/**
 * Clase de configuración que define los beans necesarios para el manejo de órdenes de pago y actividades
 * de entidad dentro de la aplicación.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Configuration
public class ApplicationConfig {

    /**
     * Crea un bean de servicio de órdenes de pago, que incluye las implementaciones de los casos de uso
     * para crear, seleccionar y actualizar órdenes de pago.
     *
     * @param ordenPagoRepositoryPort El repositorio de órdenes de pago utilizado por el servicio (required).
     * @return Un objeto OrdenPagoService que maneja las operaciones relacionadas con órdenes de pago.
     */

    /**
     * Crea un bean del repositorio de órdenes de pago utilizando el adaptador JPA.
     *
     * @param jpaOrdenPagoRepositoryAdapter El adaptador JPA para el repositorio de órdenes de pago (required).
     * @return Un objeto OrdenPagoRepositoryPort que permite acceder a las operaciones de datos de órdenes de pago.
     */
    @Bean
    public OrdenPagoRepositoryPort ordenPagoRepositoryPort(JpaOrdenPagoRepositoryAdapter jpaOrdenPagoRepositoryAdapter) {
        return jpaOrdenPagoRepositoryAdapter;
    }

    /**
     * Crea un bean de servicio de actividad de entidad, que incluye la implementación del caso de uso
     * para manejar actividades de entidad.
     *
     * @param actividadEntidadRepositoryPort El repositorio de actividades de entidad utilizado por el servicio (required).
     * @return Un objeto ActividadEntidadService que maneja las operaciones relacionadas con actividades de entidad.
     */
    @Bean
    public ActividadEntidadService actividadEntidadService(ActividadEntidadRepositoryPort actividadEntidadRepositoryPort){
        return  new ActividadEntidadService((new ActividadEntidadUseCaseImpl(actividadEntidadRepositoryPort)));
    }

    /**
     * Crea un bean del repositorio de actividades de entidad utilizando el adaptador JPA.
     *
     * @param jpaActividadEntidadRepositoryAdapter El adaptador JPA para el repositorio de actividades de entidad (required).
     * @return Un objeto ActividadEntidadRepositoryPort que permite acceder a las operaciones de datos de actividades de entidad.
     */
    @Bean
    public ActividadEntidadRepositoryPort actividadEntidadRepositoryPort(JpaActividadEntidadRepositoryAdapter jpaActividadEntidadRepositoryAdapter){
        return jpaActividadEntidadRepositoryAdapter;
    }
}
