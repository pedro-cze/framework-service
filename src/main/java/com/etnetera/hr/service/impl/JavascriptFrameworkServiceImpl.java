package com.etnetera.hr.service.impl;

import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.error.FrameworkAlreadyExistsException;
import com.etnetera.hr.error.FrameworkNotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.search.FrameworkSpecificationBuilder;
import com.etnetera.hr.search.SearchParam;
import com.etnetera.hr.service.JavascriptFrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService {

    private static final String WITH_DELIMITER = "(?<=%1$s)|(?=%1$s)";

    private final JavaScriptFrameworkRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<JavaScriptFramework> fetchArticles() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public JavaScriptFramework createFramework(final JavaScriptFramework javaScriptFramework) {
        final var framework = repository.findByName(javaScriptFramework.getName());
        if (framework.isPresent()) {
            throw new FrameworkAlreadyExistsException("Framework already exists.");
        }
        return repository.save(javaScriptFramework);
    }

    @Override
    @Transactional
    public UpdateResult updateFramework(final Long id, final JavaScriptFramework javaScriptFramework) {
        final var entity = repository.findById(id);
        repository.save(javaScriptFramework);
        if (entity.isPresent()) {
            return UpdateResult.UPDATED_NO_CONTENT;
        }
        return UpdateResult.CREATED;
    }

    @Override
    @Transactional
    public void deleteFramework(final Long id) {
        final var entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new FrameworkNotFoundException("Framework for given id not found.");
        }
        repository.delete(entity.get());
    }

    @Transactional
    public List<JavaScriptFramework> search(List<SearchParam> searchParams) {
        final var specificationBuilder = new FrameworkSpecificationBuilder(searchParams);
        final var specification = specificationBuilder.build();
        return repository.findAll(specification);
    }

}
