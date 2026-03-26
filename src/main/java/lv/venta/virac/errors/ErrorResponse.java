package lv.venta.virac.errors;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

	private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<FieldErrorDetail> errors;
 
    public ErrorResponse(int status, String message, List<FieldErrorDetail> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
