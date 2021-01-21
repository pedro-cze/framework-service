package com.etnetera.hr.controller;

import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.mapper.DomainMapper;
import com.etnetera.hr.service.JavascriptFrameworkService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import java.util.List;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@Data
@RestController
@RequiredArgsConstructor
public class JavaScriptFrameworkController {

	private final DomainMapper domainMapper;
	private final JavascriptFrameworkService javascriptFrameworkService;

	@GetMapping("/frameworks")
	public ResponseEntity<List<JavaScriptFrameworkDto>> frameworks() {
		return ResponseEntity.ok(domainMapper.toDtos(javascriptFrameworkService.fetchArticles()));
	}

}
