package lv.venta.virac.export;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.service.ICRUDPlanService;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ExportController {
	
	private final PlanExportService exportService;
    private final ICRUDPlanService planService; // tavs serviss, kas dabū DTO

    @GetMapping("/docx/{id}")
    public ResponseEntity<byte[]> exportDocx(@PathVariable int id) throws Exception {

        FullPlanDTO dto = planService.retrieveFullPlan(id);

        byte[] file = exportService.generateDocx(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable int id) throws Exception {

        FullPlanDTO dto = planService.retrieveFullPlan(id);

        byte[] docx = exportService.generateDocx(dto);
        byte[] pdf = exportService.convertToPdf(docx);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
