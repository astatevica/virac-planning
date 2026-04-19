package lv.venta.virac.export;

import lv.venta.virac.dto.FullPlanDTO;

public interface PlanExportService {

	public abstract byte[] generateDocx(FullPlanDTO dto) throws Exception;
}
