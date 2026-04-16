package lv.venta.virac.export;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import lv.venta.virac.dto.FullPlanDTO;

@Service
public class PlanExportServiceImpl implements PlanExportService{

	@Override
	public byte[] generateDocx(FullPlanDTO dto) throws Exception {
		XWPFDocument document = new XWPFDocument();

        // ===== TITLE =====
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = title.createRun();
        run.setText("Plan");
        run.setBold(true);
        run.setFontSize(16);

        // ===== TABLE =====
        XWPFTable table = document.createTable();

        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Activity");
        header.addNewTableCell().setText("Planned");
        header.addNewTableCell().setText("Done");
        
		addRow(table, "Project aplications",
		                dto.getProjApplicSub(), dto.getProjApplicSubEnd());
		
		addRow(table,"Projects",
			    String.valueOf(dto.getNumOfProjects()),
			    dto.getProjects().stream()
		        .map(p -> p.getName()
		            + " (No: " + p.getNumber()
		            + ", Acronym: " + p.getAcronym()
		            + ", " + p.getStartDate()
		            + " - " + p.getEndDate()
		            + ")"
		        )
		        .collect(Collectors.joining("\n"))
		);
                
        addRow(table, "Articles",
        		String.valueOf(dto.getNumOfArticles()), 
                dto.getArticles().stream().map(a -> a.getName()
    		            + " (CoAuthors: " + a.getCoAuthors()
    		            + ", Journal ID: " + a.getIdJournal()
    		            + ", Article comments: " + a.getArticleComments()
    		            + ", Publication link: " + a.getPublicationLink()
    		            + ")"
    		        )
    		        .collect(Collectors.joining("\n"))
        );
        
        addRow(table, "Conferences",
                dto.getPartInConf(), dto.getPartInConfEnd());
        
        addRow(table, "Comments about conferences",
                dto.getComAbConf(), dto.getComAbConfEnd());
        
        addRow(table, "Courses",
        		String.valueOf(dto.getNumOfCourses()), 
                dto.getCourses().stream().map(c -> c.getName()
    		            + " ( ECTS: " + c.getEctsCredits()
    		            + ",  Semester: " + c.getSemester()
    		            + ",  Faculty: " + c.getFaculty()
    		            + ",  Publication link: " + c.getWorkDone()
    		            + ")"
    		        )
    		        .collect(Collectors.joining("\n"))
    	);
        
        addRow(table, "Student Work",
        		String.valueOf(dto.getStudentWork()), 
                dto.getStudentWork().stream().map(w -> w.getName()
    		            + " ( Student name: " + w.getStudentName()
    		            + ",  Student surname: " + w.getStudentSurname()
    		            + ",  Degree: " + w.getDegree()
    		            + ",  Work done: " + w.getWorkDone()
    		            + ")"
    		        )
    		        .collect(Collectors.joining("\n"))
    	);
        
        addRow(table, "Research",
                dto.getPromoOfResearch(), dto.getPromoOfResearchEnd());
        
        addRow(table, "Administrative work",
                dto.getAdminWork(), dto.getAdminWorkEnd());
        
		addRow(table, "Skill development",
		                dto.getSkillsDevelopment(), dto.getSkillsDevelopmentEnd());
              
        addRow(table, "Seminars",
                dto.getParticipationInSeminars(), dto.getParticipationInSeminarsEnd());

        addRow(table, "Other",
                dto.getOtherJobs(), dto.getOtherJobsEnd());

        // ===== LISTS =====
//        addList(document, "Kursi", dto.getCourses().stream()
//                .map(c -> c.getName()).toList());
//
//        addList(document, "Projekti", dto.getProjects().stream()
//                .map(p -> p.getName()).toList());
//
//        addList(document, "Raksti", dto.getArticles().stream()
//                .map(a -> a.getName()).toList());
//
//        addList(document, "Studentu darbi", dto.getStudentWork().stream()
//                .map(w -> w.getName()).toList());

        // ===== RETURN =====
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();

        return out.toByteArray();
	}
	
	private void addRow(XWPFTable table, String section, String planned, String done) {
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText(section);
        row.getCell(1).setText(planned != null ? planned : "");
        row.getCell(2).setText(done != null ? done : "");
    }

    private void addList(XWPFDocument doc, String titleText, List<String> items) {
        XWPFParagraph title = doc.createParagraph();
        XWPFRun run = title.createRun();
        run.setBold(true);
        run.setText(titleText + ":");

        if (items != null) {
            for (String item : items) {
                XWPFParagraph p = doc.createParagraph();
                p.createRun().setText("- " + item);
            }
        }
    }

	@Override
	public byte[] convertToPdf(byte[] docxBytes) throws Exception {
		File tempDocx = File.createTempFile("plan", ".docx");
        File tempPdf = new File(tempDocx.getParent(), "plan.pdf");

        Files.write(tempDocx.toPath(), docxBytes);

        ProcessBuilder pb = new ProcessBuilder(
                "soffice",
                "--headless",
                "--convert-to", "pdf",
                tempDocx.getAbsolutePath(),
                "--outdir", tempDocx.getParent()
        );

        Process process = pb.start();
        process.waitFor();

        byte[] pdfBytes = Files.readAllBytes(tempPdf.toPath());

        tempDocx.delete();
        tempPdf.delete();

        return pdfBytes;
	}

}
