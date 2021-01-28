package com.etnetera.hr;

import com.etnetera.hr.dto.HypeLevel;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.search.FrameworkSearchSpecification;
import com.etnetera.hr.search.SearchParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SearchSpecificationTest {

    @Autowired
    public JavaScriptFrameworkRepository repository;
    private DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    public void init() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    @Transactional
    public void testEqualsSearchParam_expectingOnlyOneFramework() {

        final var angular = framework("Angular", LocalDate.now(), HypeLevel.AT_THE_PEAK, new HashSet<>());
        final var react = framework("React", LocalDate.now(), HypeLevel.CLIMBING_THE_SLOPE, new HashSet<>());
        repository.save(angular);
        repository.save(react);

        FrameworkSearchSpecification spec =
                new FrameworkSearchSpecification(new SearchParam("name", ":", "React"));

        List<JavaScriptFramework> results = repository.findAll(spec);

        assertEquals(1, results.size());
        assertEquals(react, results.get(0));
    }

    @Test
    @Transactional
    public void testGraterThanSearchParam_expectingOnlyOneFramework() {

        final var angular = framework("Angular", LocalDate.now().minusDays(1), HypeLevel.AT_THE_PEAK, new HashSet<>());
        final var react = framework("React", LocalDate.now().plusDays(1), HypeLevel.CLIMBING_THE_SLOPE, new HashSet<>());
        repository.save(angular);
        repository.save(react);

        FrameworkSearchSpecification spec =
                new FrameworkSearchSpecification(new SearchParam("deprecationDate", ">", "2021-01-28"));

        List<JavaScriptFramework> results = repository.findAll(spec);

        assertEquals(1, results.size());
        assertEquals(react, results.get(0));
    }

    @Test
    @Transactional
    public void testLessThanSearchOperation_expectingTwoOfThreeFrameworks() {

        final var angular = framework("Angular", LocalDate.parse("2021-01-27", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        final var react = framework("React", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.CLIMBING_THE_SLOPE, new HashSet<>());
        final var vue = framework("Vue", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        repository.save(angular);
        repository.save(react);
        repository.save(vue);

        FrameworkSearchSpecification spec =
                new FrameworkSearchSpecification(new SearchParam("deprecationDate", "<", "2021-01-28"));

        List<JavaScriptFramework> results = repository.findAll(spec);

        assertEquals(1, results.size());
        assertTrue(results.contains(angular));
    }

    @Test
    @Transactional
    public void testTwoSpecifications_expectingOneFramework() {
        final var angular = framework("Angular", LocalDate.parse("2021-01-27", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        final var react = framework("React", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.CLIMBING_THE_SLOPE, new HashSet<>());
        final var vue = framework("Vue", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        repository.save(angular);
        repository.save(react);
        repository.save(vue);

        FrameworkSearchSpecification spec1 =
                new FrameworkSearchSpecification(new SearchParam("deprecationDate", ">", "2021-01-28"));

        FrameworkSearchSpecification spec2 =
                new FrameworkSearchSpecification(new SearchParam("name", ":", "React"));

        List<JavaScriptFramework> results = repository.findAll(Specification.where(spec1).and(spec2));
        assertEquals(1, results.size());
        assertTrue(results.contains(react));
    }

    @Test
    @Transactional
    public void testPartialStringGiven_expectingTwoFrameworks() {

        final var angular = framework("Angular", LocalDate.parse("2021-01-27", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        final var react = framework("React", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.CLIMBING_THE_SLOPE, new HashSet<>());
        final var reactive = framework("Reactive", LocalDate.parse("2021-01-29", dateTimeFormatter), HypeLevel.AT_THE_PEAK, new HashSet<>());
        repository.save(angular);
        repository.save(react);
        repository.save(reactive);

        FrameworkSearchSpecification spec1 =
                new FrameworkSearchSpecification(new SearchParam("deprecationDate", ">", "2021-01-28"));

        FrameworkSearchSpecification spec2 =
                new FrameworkSearchSpecification(new SearchParam("name", ":", "Rea"));

        List<JavaScriptFramework> results = repository.findAll(Specification.where(spec1).and(spec2));
        assertEquals(2, results.size());
        assertTrue(results.contains(react));
        assertTrue(results.contains(reactive));
    }

    private JavaScriptFramework framework(final String name, final LocalDate localDate, HypeLevel level, Set<String> version) {
        final var result = new JavaScriptFramework();
        result.setName(name);
        result.setDeprecationDate(localDate);
        result.setHypeLevel(level);
        result.setVersion(version);
        return result;
    }

}
