package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

  /*  @BeforeEach
    void setUp() {
        // Создаем мок репозитория
        //facultyRepository = mock(FacultyRepository.class);
        // Инициализируем сервис, передавая мок репозитория
        //facultyService = new FacultyService(facultyRepository);

    }*/

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
    void testFindByNameOrColor() {

        String facultyName = "Some Faculty";
        String color = "Some Faculty";
        List<Faculty> faculties = new ArrayList<>();
        when(facultyRepository.findByNameIgnoreCase(facultyName)).thenReturn(faculties);
        when(facultyRepository.findByNameIgnoreCase(color)).thenReturn(faculties);


        Collection<Faculty> result = facultyService.findAllbyNameIgnoreCase(facultyName);
        Collection<Faculty> result2 = facultyService.findAllbyColorIgnoreCase(color);


        assertEquals(faculties, result);
        verify(facultyRepository, times(1)).findByNameIgnoreCase(facultyName);
        verify(facultyRepository, times(1)).findByNameIgnoreCase(color);
    }

    @Test
    public void testDeleteFaculty_ExistingFaculty_SuccessfulDeletion() {
        // Устанавливаем ID факультета, который существует в репозитории
        long existingFacultyId = 1L;

        // При вызове метода findById с переданным ID, возвращаем факультет (сущность)
        when(facultyRepository.findById(existingFacultyId)).thenReturn(Optional.of(new Faculty()));

        // Вызываем метод deleteFaculty и ожидаем true, так как удаление должно быть успешным
        assertTrue(facultyService.deleteFaculty(existingFacultyId));

        // Проверяем, что метод delete был вызван один раз с соответствующей сущностью
        verify(facultyRepository, times(1)).delete(any(Faculty.class));
    }

    @Test
    public void testDeleteFaculty_NonExistentFaculty_ReturnFalse() {
        // Устанавливаем ID факультета, которого нет в репозитории
        long nonExistentFacultyId = 100L;

        // При вызове метода findById с переданным ID, возвращаем пустой Optional (факультет не найден)
        when(facultyRepository.findById(nonExistentFacultyId)).thenReturn(Optional.empty());

        // Вызываем метод deleteFaculty и ожидаем false, так как удаление не должно быть успешным
        assertFalse(facultyService.deleteFaculty(nonExistentFacultyId));

        // Проверяем, что метод delete не был вызван, так как факультет не найден
        verify(facultyRepository, never()).delete(any(Faculty.class));
    }

    @Test
    public void testDeleteFaculty_ExistingFaculty_SuccessfullDeletion() {
        // Устанавливаем ID факультета, который существует в репозитории
        long existingFacultyId = 1L;

        // При вызове метода findById с переданным ID, возвращаем факультет (сущность)
        when(facultyRepository.findById(existingFacultyId)).thenReturn(Optional.of(new Faculty()));

        // Вызываем метод deleteFaculty и ожидаем true, так как удаление должно быть успешным
        assertTrue(facultyService.deleteFaculty(existingFacultyId));

        // Проверяем, что метод delete был вызван один раз с соответствующей сущностью
        verify(facultyRepository, times(1)).delete(any(Faculty.class));
    }

    @Test
    public void testDeleteFaculty_NonExistentFaculty_ReturnsFalse() {
        // Устанавливаем ID факультета, которого нет в репозитории
        long nonExistentFacultyId = 100L;

        // При вызове метода findById с переданным ID, возвращаем пустой Optional (факультет не найден)
        when(facultyRepository.findById(nonExistentFacultyId)).thenReturn(Optional.empty());

        // Вызываем метод deleteFaculty и ожидаем false, так как удаление не должно быть успешным
        assertFalse(facultyService.deleteFaculty(nonExistentFacultyId));

        // Проверяем, что метод delete не был вызван, так как факультет не найден
        verify(facultyRepository, never()).delete(any(Faculty.class));
    }
}




