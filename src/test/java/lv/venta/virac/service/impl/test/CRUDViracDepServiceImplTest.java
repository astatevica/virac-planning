package lv.venta.virac.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.Optional;

import javax.naming.NotContextException;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.service.impl.CRUDViracDepServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CRUDViracDepServiceImplTest {

	@Mock
    private IViracDepartmentRepo depRepo;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Filter filter;

    @InjectMocks
    private CRUDViracDepServiceImpl depService;

    private static ViracDepartment dep;

    private static ArrayList<ViracDepartment> depList;

    @BeforeEach
    void setUp() {
        dep = new ViracDepartment("IT","Janis","Berzins");
        dep.setDeleted(false);

        depList = new ArrayList<>();
        depList.add(dep);
    }

    @Test
    void testRetrieveAll() throws Exception {

        when(entityManager.unwrap(Session.class)).thenReturn(session);

        when(session.enableFilter("deletedDepartmentFilter")).thenReturn(filter);

        when(depRepo.findAll()).thenReturn(depList);

        ArrayList<ViracDepartment> result = depService.retrieveAll();

        assertEquals(1, result.size());

        verify(session).disableFilter("deletedDepartmentFilter");
    }

    @Test
    void testRetrieveAllEmpty() {

        when(entityManager.unwrap(Session.class)).thenReturn(session);

        when(session.enableFilter("deletedDepartmentFilter")).thenReturn(filter);

        when(depRepo.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotContextException.class,() -> depService.retrieveAll());
    }

    @Test
    void testRetrieveById() throws Exception {

        when(depRepo.findById(1)).thenReturn(Optional.of(dep));

        ViracDepartment result = depService.retrieveById(1);

        assertNotNull(result);

        assertEquals("IT",result.getName());
    }

    @Test
    void testRetrieveByIdInvalid() {

        assertThrows(Exception.class,() -> depService.retrieveById(0));
    }

    @Test
    void testCreate() throws Exception {

        when(depRepo.findAll()).thenReturn(new ArrayList<>());

        depService.create("Finance","Anna","Ozola");

        verify(depRepo).save(any(ViracDepartment.class));
    }

    @Test
    void testCreateDuplicate() {

        when(depRepo.findAll()).thenReturn(depList);

        assertThrows(Exception.class,() -> depService.create("IT","Janis","Berzins"));
    }

    @Test
    void testUpdateById() throws Exception {

        when(depRepo.findById(1)).thenReturn(Optional.of(dep));

        depService.updateById(1,"Finance","Anna","Ozola");

        assertEquals("Finance",dep.getName());

        assertEquals("Anna",dep.getHeadName());

        verify(depRepo).save(dep);
    }

    @Test
    void testDeleteById() throws Exception {

        when(depRepo.findById(1)).thenReturn(Optional.of(dep));

        depService.deleteById(1);

        assertTrue(dep.isDeleted());

        verify(depRepo).save(dep);
    }

}
