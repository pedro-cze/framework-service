package com.etnetera.hr.service.impl;

import com.etnetera.hr.entity.JavaScriptFramework;
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
}
