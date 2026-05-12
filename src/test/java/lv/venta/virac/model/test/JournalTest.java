package lv.venta.virac.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.ScientificArticles;

public class JournalTest {
	
	private static Validator validator;
	private static Journal defaultJournal;
	private static Journal journal;
	private static Journal nullJournal;
	
	
	@BeforeAll
	static void setUp() {
		defaultJournal = new Journal();
		journal = new Journal("Science Journal");
		nullJournal = new Journal(null);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}
	
	@Test
	void testConstructor() {
	    assertEquals("Science Journal", journal.getName());
	}
	

	@Test
	void testDefaultValues() {
	    assertFalse(defaultJournal.isDeleted());
	    assertNull(defaultJournal.getName());
	    assertNull(defaultJournal.getScientificArticles());
	}
	
	@Test
	void testSetters() {
	    defaultJournal.setName("Physics Journal");
	    assertEquals("Physics Journal", defaultJournal.getName());
	}
	
	@Test
	void testNameNullValidation() {

	    Set<ConstraintViolation<Journal>> result =
	            validator.validate(nullJournal);

	    assertEquals(1, result.size());//just one validation should be failed
	    assertFalse(result.isEmpty());
	}
	
	@Test
	void testDeletedFlag() {
	    assertFalse(journal.isDeleted());
	    journal.setDeleted(true);
	    assertTrue(journal.isDeleted());
	}
	
	@Test
	void testScientificArticlesSetter() {
	    Collection<ScientificArticles> articles = new ArrayList<>();
	    journal.setScientificArticles(articles);
	    assertEquals(articles, journal.getScientificArticles());
	}
	
	@Test
	void testIdDefaultValue() {
	    assertEquals(0, defaultJournal.getIdJournal());
	}
	
}
