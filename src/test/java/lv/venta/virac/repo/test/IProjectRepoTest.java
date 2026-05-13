package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lv.venta.virac.model.Project;
import lv.venta.virac.repo.IProjectRepo;

@DataJpaTest
public class IProjectRepoTest {

	@Autowired
    private IProjectRepo projectRepo;

    @Test
    void testFindByNumber() {
        Project p = new Project(
                "AI Research",
                12345,
                null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "AIR"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.findByNumber(12345);
        assertEquals(1, result.size());
        assertEquals("AI Research", result.get(0).getName());
    }

    @Test
    void testFindByStartDate() {

        LocalDate start = LocalDate.of(2025, 1, 1);

        Project p = new Project(
                "Project Start",
                100,
                null,
                start,
                LocalDate.of(2025, 12, 31),
                "PS"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.findByStartDate(start);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByEndDate() {

        LocalDate end = LocalDate.of(2025, 12, 31);

        Project p = new Project(
                "Project End",
                200,
                null,
                LocalDate.of(2025, 1, 1),
                end,
                "PE"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.findByEndDate(end);
        assertEquals(1, result.size());
    }

    @Test
    void testSearchProjects() {

        Project p = new Project(
                "Machine Learning Project",
                300,
                null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "MLP"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.searchProjects("learning");
        assertEquals(1, result.size());
    }

    @Test
    void testSearchProjectsCaseInsensitive() {

        Project p = new Project(
                "Deep Learning",
                400,
                null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "DL"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.searchProjects("deep");
        assertFalse(result.isEmpty());
    }

    @Test
    void testSearchProjectsNotFound() {

        Project p = new Project(
                "Physics Project",
                500,
                null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "PP"
        );

        projectRepo.save(p);
        ArrayList<Project> result = projectRepo.searchProjects("biology");
        assertTrue(result.isEmpty());
    }

}
