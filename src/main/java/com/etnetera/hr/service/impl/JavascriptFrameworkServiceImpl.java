package com.etnetera.hr.service.impl;

import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.error.FrameworkAlreadyExistsException;
import com.etnetera.hr.error.FrameworkNotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavascriptFrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService {

    private final JavaScriptFrameworkRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<JavaScriptFramework> fetchArticles() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public JavaScriptFramework createFramework(JavaScriptFramework javaScriptFramework) {
        var framework = repository.findByName(javaScriptFramework.getName());
        if (framework.isPresent()) {
            throw new FrameworkAlreadyExistsException("Framework already exists.");
        }
        return repository.save(javaScriptFramework);
    }

    @Override
    @Transactional
    public UpdateResult updateFramework(Long id, JavaScriptFramework javaScriptFramework) {
        var entity = repository.findById(id);
        repository.save(javaScriptFramework);
        if (entity.isPresent()) {
            return UpdateResult.UPDATED_NO_CONTENT;
        }
        return UpdateResult.CREATED;
    }

    @Override
    @Transactional
    public void deleteFramework(Long id) {
        var entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new FrameworkNotFoundException("");
        }
        repository.delete(entity.get());
    }
}
