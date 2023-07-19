package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFaculty() {

        Faculty faculty = new Faculty();
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);


        Faculty result = facultyService.createFaculty(faculty);


        assertEquals(faculty, result);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void testFindFacultyById() {

        long facultyId = 1;
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));


        Faculty result = facultyService.findFacultyById(facultyId);


        assertEquals(faculty, result);

    }

    @Test
    void testFindByName() {

        String facultyName = "Some Faculty";
        List<Faculty> faculties = new ArrayList<>();
        when(facultyRepository.findByName(facultyName)).thenReturn(faculties);


        List<Faculty> result = facultyService.findByName(facultyName);


        assertEquals(faculties, result);
        verify(facultyRepository, times(1)).findByName(facultyName);
    }

    @Test
    void testEditFaculty() {

        Faculty faculty = new Faculty();
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);


        Faculty result = facultyService.editFaculty(faculty);


        assertEquals(faculty, result);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void testDeleteFaculty() {

        long facultyId = 1;


        facultyService.deleteFaculty(facultyId);


        verify(facultyRepository, times(1)).deleteById(facultyId);
    }

    @Test
    void testFindByColor() {

        String color = "blue";
        List<Faculty> faculties = new ArrayList<>();
        when(facultyRepository.findByColor(color)).thenReturn(faculties);


        Collection<Faculty> result = facultyService.findByColor(color);


        assertEquals(faculties, result);

    }
}

