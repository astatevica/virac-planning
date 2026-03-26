package lv.venta.virac.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorDetail {

	private String field;
    private String message;
    private Object rejectedValue;
 
    public FieldErrorDetail(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }
}
