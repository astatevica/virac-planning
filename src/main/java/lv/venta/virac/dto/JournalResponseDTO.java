package lv.venta.virac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JournalResponseDTO {
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
	private String name;

}
