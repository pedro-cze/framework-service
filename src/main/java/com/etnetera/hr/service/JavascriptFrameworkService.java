package com.etnetera.hr.service;

import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.search.SearchParam;

import java.util.List;

public interface JavascriptFrameworkService {

    List<JavaScriptFramework> fetchArticles();

    JavaScriptFramework createFramework(JavaScriptFramework javaScriptFramework);

    UpdateResult updateFramework(Long id, JavaScriptFramework javaScriptFramework);

    void deleteFramework(Long id);

    List<JavaScriptFramework> search(List<SearchParam> searchParamSet);

}
