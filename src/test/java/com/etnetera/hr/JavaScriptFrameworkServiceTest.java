package com.etnetera.hr;

import com.etnetera.hr.dto.HypeLevel;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavascriptFrameworkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JavaScriptFrameworkServiceTest {

    @Autowired
    public JavascriptFrameworkService javascriptFrameworkService;
    @Autowired
    public JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Test
    public void fetchAllArticlesTest() {
    }

    @Test
    @Transactional
    public void createFrameworkTest() {
        final var expectedDate = LocalDate.now();
        javascriptFrameworkService.createFramework(
                mockFramework(
                        "Angular",
                        expectedDate,
                        HypeLevel.AT_THE_PEAK,
                        Set.of("1.0", "2.0")
                )
        );
        var fetchResult = javaScriptFrameworkRepository.findByName("Angular").get();
        assertNotNull(fetchResult);
        assertEquals("Angular", fetchResult.getName());
        assertEquals(HypeLevel.AT_THE_PEAK, fetchResult.getHypeLevel());
        assertEquals(expectedDate, fetchResult.getDeprecationDate());
        assertFalse(fetchResult.getVersion().isEmpty());
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
