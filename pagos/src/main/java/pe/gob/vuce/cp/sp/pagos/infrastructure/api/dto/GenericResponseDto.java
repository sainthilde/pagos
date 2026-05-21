package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseDto<T> {
    ResponseMetaDataDto meta;
    List<T> data;
}
