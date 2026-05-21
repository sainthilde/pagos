package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;


public class OrdenPagoErrorResponse {
    private Integer statusCode;
    private String body;
    private Integer ordenPagoId;

    public OrdenPagoErrorResponse(Integer statusCode, String body, Integer ordenPagoId) {
        this.statusCode = statusCode;
        this.body = body;
        this.ordenPagoId = ordenPagoId;
    }

    // Getters
    public Integer getStatusCode() { return statusCode; }
    public String getBody() { return body; }
    public Integer getOrdenPagoId() { return ordenPagoId; }
}