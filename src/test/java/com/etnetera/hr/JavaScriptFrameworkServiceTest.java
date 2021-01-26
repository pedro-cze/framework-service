package com.etnetera.hr;

import com.etnetera.hr.dto.HypeLevel;
import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.error.FrameworkAlreadyExistsException;
import com.etnetera.hr.error.FrameworkNotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavascriptFrameworkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JavaScriptFrameworkServiceTest {

    @Autowired
    public JavascriptFrameworkService javascriptFrameworkService;
    @Autowired
    public JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Test
    @Transactional
    @DisplayName("Create single framework - exception success")
    public void createFrameworkTest() {
        final var expectedDate = LocalDate.now();
        final var angular = mockFramework("Angular", expectedDate, HypeLevel.AT_THE_PEAK, Set.of("1.0", "2.0"));
        javascriptFrameworkService.createFramework(angular);
        var fetchResult = javaScriptFrameworkRepository.findByName("Angular").get();
        assertNotNull(fetchResult);
        assertEquals("Angular", fetchResult.getName());
        assertEquals(HypeLevel.AT_THE_PEAK, fetchResult.getHypeLevel());
        assertEquals(expectedDate, fetchResult.getDeprecationDate());
        assertFalse(fetchResult.getVersion().isEmpty());
    }

    @Test
    @DisplayName("Create the same framework twice - expecting exception")
    public void createFramework_expectConflictTest() {
        final var angular = mockFramework("Angular", LocalDate.now(), HypeLevel.AT_THE_PEAK, Set.of("99"));
        javascriptFrameworkService.createFramework(angular);
        assertThrows(FrameworkAlreadyExistsException.class, () -> javascriptFrameworkService.createFramework(angular));
    }

    @Test
    @DisplayName("Save several articles and fetch all - expect non empty list")
    public void saveAndFetchAllArticlesTest() {
        final var expectedDate = LocalDate.now();
        final var mockReact = mockFramework("React", expectedDate, HypeLevel.AT_THE_PEAK, Set.of("1", "2"));
        final var mockVue = mockFramework("Vue JS", expectedDate, HypeLevel.CLIMBING_THE_SLOPE, Set.of("2.12"));
        javascriptFrameworkService.createFramework(mockReact);
        javascriptFrameworkService.createFramework(mockVue);
        var result = javascriptFrameworkService.fetchArticles();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Update non existing framework and fetch - expect the framework get created")
    public void updateNonExitingFrameworkTest() {
        final var emptyRes = javascriptFrameworkService.fetchArticles();
        assertTrue(emptyRes.isEmpty());
        final var mockFramework = mockFramework("React", LocalDate.now(), HypeLevel.CLIMBING_THE_SLOPE, Set.of());
        var updateResult = javascriptFrameworkService.updateFramework(124L, mockFramework);
        assertEquals(UpdateResult.CREATED, updateResult);
        var result = javascriptFrameworkService.fetchArticles();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        final JavaScriptFramework retrievedFramework = result.get(0);
        assertNotNull(retrievedFramework);
        assertEquals(Long.valueOf(1), retrievedFramework.getId());
    }

    @Test
    @Transactional
    @DisplayName("Update existing framework")
    public void updateExistingFrameworkTest() {
        final var version = new HashSet<String>();
        version.add("1.0");

        final var svelte = mockFramework("Svelte JS", LocalDate.now(), HypeLevel.ENTERING_THE_PLATEAU, version);
        javascriptFrameworkService.createFramework(svelte);
        var saved = javaScriptFrameworkRepository.findByName("Svelte JS");
        assertTrue(saved.isPresent());
        var svelteId = saved.get().getId();

        final var versionUpdate = new HashSet<String>();
        versionUpdate.add("1.0");
        versionUpdate.add("1.1");
        final var update = mockFramework("Svelte JS", LocalDate.now(), HypeLevel.AT_THE_PEAK, versionUpdate);
        update.setId(svelteId);
        var updateResult = javascriptFrameworkService.updateFramework(svelteId, update);
        assertEquals(UpdateResult.UPDATED_NO_CONTENT, updateResult);

        final var updated = javaScriptFrameworkRepository.findByName("Svelte JS").get();
        assertEquals(svelteId, updated.getId());
        assertEquals(svelte.getName(), updated.getName());
        assertEquals(HypeLevel.AT_THE_PEAK, updated.getHypeLevel());
        assertEquals(Set.of("1.0", "1.1"), updated.getVersion());
    }

    @Test
    @DisplayName("Delete non existing framework - expecting FrameworkNotFoundException")
    public void deleteNonExistingFrameworkTest() {
        assertThrows(FrameworkNotFoundException.class, () -> javascriptFrameworkService.deleteFramework(1L));
    }

    @Test
    @DisplayName("Delete existing framework")
    public void deleteFrameworkTest() {
        final var mockReact = mockFramework("React", LocalDate.now(), HypeLevel.AT_THE_PEAK, Set.of("1", "2"));
        final var result = javascriptFrameworkService.createFramework(mockReact);
        assertFalse(javaScriptFrameworkRepository.findAll().isEmpty());
        javascriptFrameworkService.deleteFramework(result.getId());
        assertTrue(javaScriptFrameworkRepository.findAll().isEmpty());
    }

    private JavaScriptFramework mockFramework(final String name, final LocalDate date, final HypeLevel hypeLevel, final Set<String> version) {
        var result = new JavaScriptFramework();
        result.setName(name);
        result.setDeprecationDate(date);
        result.setHypeLevel(hypeLevel);
        result.setVersion(version);
        return result;
    }
}
