package lv.venta.virac.export;

import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import lv.venta.virac.dto.FullPlanDTO;

@Service
public class PlanExportServiceImpl implements PlanExportService{

	@Override
	public byte[] generateDocx(FullPlanDTO dto) throws Exception {
		//MS Word File Generation
		XWPFDocument document = new XWPFDocument();

        //Formatting Title
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        title.setSpacingAfter(300);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("ID employee: " + dto.getIdEmployee() + " ID Year" + 
        		dto.getIdYear() + " Plan");
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setFontFamily("Times New Roman");

        // Table
        XWPFTable table = document.createTable();
        
        //Table formating
        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 2, 0, "000000");
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 2, 0, "000000");
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 2, 0, "000000");
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 2, 0, "000000");

        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
        

        //Table header
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Activity");
        header.addNewTableCell().setText("Planned");
        header.addNewTableCell().setText("Done");
        
		addRow(table, "Project aplications",
		                dto.getProjApplicSub(), dto.getProjApplicSubEnd());
		
		//Formating incoming list to render it in next row
	    String projectText = dto.getProjects().stream()
        .map(p -> p.getName()
            + " (No: " + p.getNumber()
            + ", Acronym: " + p.getAcronym()
            + ", " + p.getStartDate()
            + " - " + p.getEndDate()
            + ")"
        )
        .collect(Collectors.joining("\n"));
	    //Adding formated text to word document
	    addRow(table,"Projects", String.valueOf(dto.getNumOfProjects()), projectText);
               
	    //Formating incoming list to render it in next row
	    String articleText = dto.getArticles().stream().map(a -> a.getName()
	            + " (CoAuthors: " + a.getCoAuthors()
	            + ", Journal ID: " + a.getIdJournal()
	            + ", Article comments: " + a.getArticleComments()
	            + ", Publication link: " + a.getPublicationLink()
	            + ")"
	        )
	        .collect(Collectors.joining("\n"));
	    //Adding formated text to word document
        addRow(table, "Articles",String.valueOf(dto.getNumOfArticles()), articleText);
        
        addRow(table, "Conferences",
                dto.getPartInConf(), dto.getPartInConfEnd());
        
        addRow(table, "Comments about conferences",
                dto.getComAbConf(), dto.getComAbConfEnd());
        
        //Formating incoming list to render it in next row
        String courseText = dto.getCourses().stream().map(c -> c.getName()
	            + " ( ECTS: " + c.getEctsCredits()
	            + ",  Semester: " + c.getSemester()
	            + ",  Faculty: " + c.getFaculty()
	            + ",  Publication link: " + c.getWorkDone()
	            + ")"
	        )
	        .collect(Collectors.joining("\n"));
        //Adding formated text to word document
        addRow(table, "Courses",String.valueOf(dto.getNumOfCourses()), courseText);
        
        //Formating incoming list to render it in next row
        String studentWorkText = dto.getStudentWork().stream().map(w -> w.getName()
	            + " ( Student name: " + w.getStudentName()
	            + ",  Student surname: " + w.getStudentSurname()
	            + ",  Degree: " + w.getDegree()
	            + ",  Work done: " + w.getWorkDone()
	            + ")"
	        )
	        .collect(Collectors.joining("\n"));
        //Adding formated text to word document
        addRow(table, "Student Work",String.valueOf(dto.getNumOfStudWork()), studentWorkText);
        
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

        // Return
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();

        return out.toByteArray();
	}
	
	private void setMultilineCell(XWPFTableCell cell, String text) {

	    cell.removeParagraph(0);

	    if (text == null) return;

	    for (String line : text.split("\n")) {
	        XWPFParagraph p = cell.addParagraph();
	        XWPFRun r = p.createRun();
	        r.setText(line);
	    }
	}
	
	private void addRow(XWPFTable table, String section, String planned, String done) {
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText(section);
        
        setMultilineCell(row.getCell(1), planned);
        setMultilineCell(row.getCell(2), done);
    }

}
