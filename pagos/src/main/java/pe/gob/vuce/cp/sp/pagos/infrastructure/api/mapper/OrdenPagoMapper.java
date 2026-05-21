package pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;

import java.util.List;

/**
 * Mapper que se encarga de la conversión entre la entidad de Orden de Pago, el modelo de negocio y los DTOs utilizados en la API.
 *
 * Utiliza MapStruct para la generación automática de la implementación del mapeo entre los distintos objetos.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrdenPagoMapper {
    /**
     * Convierte un modelo de OrdenPago en un DTO de OrdenPagoResponseDto.
     *
     * @param ordenPago El modelo a convertir.
     * @return El DTO de OrdenPagoResponseDto correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "fechaPagado", expression = "java(ordenPago.getFechaPagado() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(ordenPago.getFechaPagado()) : null)"),
            @Mapping(target = "fechaAnulacionCpb", expression = "java(ordenPago.getFechaAnulacionCpb() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(ordenPago.getFechaAnulacionCpb()) : null)"),
            @Mapping(target = "gpMonto", source = "ordenPago.gpMonto"),
            @Mapping(target = "fechaExtornoOrdenPago", expression = "java(ordenPago.getFechaExtornoOrdenPago() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(ordenPago.getFechaExtornoOrdenPago()) : null)"),
            @Mapping(target = "fechaCreacionOrdenPago", expression = "java(ordenPago.getFechaCreacionOrdenPago() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(ordenPago.getFechaCreacionOrdenPago()) : null)"),
            @Mapping(target = "ppFechaConfGeneracionCpb", expression = "java(ordenPago.getPpFechaConfGeneracionCpb() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(ordenPago.getPpFechaConfGeneracionCpb()) : null)")

    })
    OrdenPagoResponseDto modelToDto(OrdenPago ordenPago);

    /**
     * Convierte una entidad de OrdenPagoEntity en un modelo de OrdenPago.
     *
     * @param entity La entidad a convertir.
     * @return El modelo de OrdenPago correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "fechaGeneracion", expression = "java(entity.getFechaCreacionOrdenPago() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(entity.getFechaCreacionOrdenPago()) : null)"),
            @Mapping(target = "fechaVigencia", expression = "java(entity.getFechaVencimientoOrdenPago() != null ? java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd\").withZone(java.time.ZoneId.of(\"America/Lima\")).format(entity.getFechaVencimientoOrdenPago()) : null)"),
            @Mapping(target = "estado", source = "entity.estadoOrdenPago"),
            @Mapping(target = "cpb", source = "entity.ppCpb"),
            @Mapping(target = "filenetGuid", source = "entity.pdfCpbFilenetGuid"),
            @Mapping(target = "monto", source = "entity.ppMonto"),
            @Mapping(target = "codigoOrdenPago", source = "entity.ppCodOrdenPago"),
            @Mapping(target = "ordenPagoInternaId", source = "entity.ppIdOrdenPagoInterna")
    })
    OrdenPago entityToModel(OrdenPagoEntity entity);

    /**
     * Convierte un modelo de OrdenPago en una entidad de OrdenPagoEntity.
     *
     * @param ordenPago El modelo a convertir.
     * @return La entidad de OrdenPagoEntity correspondiente.
     */

    @Mappings(value = {
            @Mapping(target = "ordenPagoId", source = "ordenPago.ordenPagoId"),
            @Mapping(target = "entidadId", source = "ordenPago.entidadId"),
            @Mapping(target = "documentoId", source = "ordenPago.documentoId"),
            @Mapping(target = "escalaId", source = "ordenPago.escalaId"),
            @Mapping(target = "rucAgente", source = "ordenPago.rucAgente"),
            @Mapping(target = "estadoOrdenPago", expression = "java(ordenPago.getEstado() != null ? ordenPago.getEstado() : \"CR\")"),
            @Mapping(target = "fechaCreacionOrdenPago", expression = "java(ordenPago.getFechaGeneracion() != null ? java.time.LocalDate.parse(ordenPago.getFechaGeneracion(), java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMddHHmmss\")).atStartOfDay(java.time.ZoneId.of(\"America/Lima\")).toInstant() : java.time.Instant.now())"),
            @Mapping(target = "fechaVencimientoOrdenPago", expression = "java(java.time.LocalDate.parse(ordenPago.getFechaVigencia(), java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd\")).atStartOfDay(java.time.ZoneId.of(\"America/Lima\")).toInstant())"),
            @Mapping(target = "ppCpb", source = "ordenPago.cpb"),
            @Mapping(target = "ppMonto", source = "ordenPago.monto"),
            @Mapping(target = "ppCodOrdenPago", source = "ordenPago.codigoOrdenPago"),
            @Mapping(target = "usuidRegAud", source = "ordenPago.usuidRegAud")
    })
    OrdenPagoEntity modelToEntity(OrdenPago ordenPago);

    /**
     * Convierte un DTO de OrdenPagoRequestDto en un modelo de OrdenPago.
     *
     * @param ordenPagoRequestDto El DTO a convertir.
     * @param user El usuario que realiza la operación, utilizado para auditoría.
     * @return El modelo de OrdenPago correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "entidadId", source = "ordenPagoRequestDto.entidadId"),
            @Mapping(target = "documentoId", source = "ordenPagoRequestDto.documentoId"),
            @Mapping(target = "escalaId", source = "ordenPagoRequestDto.escalaId"),
            @Mapping(target = "rucAgente", source = "ordenPagoRequestDto.rucAgente"),
            @Mapping(target = "fechaVigencia", source = "ordenPagoRequestDto.fechaVigencia"),
            @Mapping(target = "idComponente", source = "ordenPagoRequestDto.idComponente"),
            @Mapping(target = "textSearch", source = "ordenPagoRequestDto.textSearch"),
            @Mapping(target = "usuidRegAud", source = "user"),

    })
    OrdenPago dtoToModel(OrdenPagoRequestDto ordenPagoRequestDto, String user);
    /**
     * Convierte un DTO de OrdenPagoRequestDto en un modelo de OrdenPago, incluyendo el ID.
     *
     * @param ordenPagoRequestDto El DTO a convertir.
     * @param id El ID de la orden de pago.
     * @param user El usuario que realiza la operación, utilizado para auditoría.
     * @return El modelo de OrdenPago correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "ordenPagoId", source = "id"),
            @Mapping(target = "usuidRegAud", source = "user"),
            @Mapping(target = "usuidModAud", source = "user")
    })
    OrdenPago dtoToModel(OrdenPagoRequestDto ordenPagoRequestDto, Integer id, String user);

    /**
     * Convierte una lista de modelos de OrdenPago en una lista de DTOs de OrdenPagoResponseDto.
     *
     * @param ordenesPago La lista de modelos a convertir.
     * @return La lista de DTOs de OrdenPagoResponseDto correspondiente.
     */
    List<OrdenPagoResponseDto> listModelToListDto(List<OrdenPago> ordenesPago);

    /**
     * Convierte una lista de entidades de OrdenPagoEntity en una lista de modelos de OrdenPago.
     *
     * @param ordenPagoEntities La lista de entidades a convertir.
     * @return La lista de modelos de OrdenPago correspondiente.
     */
    List<OrdenPago> listEntityToListModel(List<OrdenPagoEntity> ordenPagoEntities);
}
