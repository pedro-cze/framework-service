package com.etnetera.hr.controller;

import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkUpdateRequest;
import com.etnetera.hr.dto.JavaScriptFrameworkRequestDto;
import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.error.FrameworkAlreadyExistsException;
import com.etnetera.hr.error.FrameworkNotFoundException;
import com.etnetera.hr.mapper.DomainMapper;
import com.etnetera.hr.service.JavascriptFrameworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera, Petr Kadlec
 *
 */
@Data
@Slf4j
@RestController
@RequestMapping("/frameworks")
@RequiredArgsConstructor
@Api(tags = {"JavaScript Frameworks"}, produces = "application/json")
public class JavaScriptFrameworkController {

	public static final String FRAMEWORK_RESOURCE_LOCATION = "/frameworks";

	private final DomainMapper domainMapper;
	private final JavascriptFrameworkService frameworkService;

	@GetMapping
	@ApiOperation(value = "Get list of all stored frameworks.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fetch successful, returning list of stored frameworks")
	})
	public ResponseEntity<List<JavaScriptFrameworkDto>> frameworks() {
		return ResponseEntity.ok(domainMapper.toDtos(frameworkService.fetchArticles()));
	}

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created new framework resource.",
					responseHeaders = { @ResponseHeader(name="Location", description = "URI location of crated resource")}),
			@ApiResponse(code = 409, message = "Conflict - there is already a framework with the same ")
	})
	public ResponseEntity<Void> createFramework(@RequestBody final JavaScriptFrameworkRequestDto requestDto) {
		try {
			var result = frameworkService.createFramework(domainMapper.toEntity(requestDto));
			return ResponseEntity.status(HttpStatus.CREATED)
					.header(HttpHeaders.LOCATION, resourceLocation(result.getId()))
					.build();
		} catch(FrameworkAlreadyExistsException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping(path = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "The resource got updated successfully."),
			@ApiResponse(code = 201, message = "The resource got created successfully."),
			@ApiResponse(code = 200, message = "The resource got updated successfully.")
	})
	public ResponseEntity<Void> updateFramework(
			@RequestBody final JavaScriptFrameworkUpdateRequest requestDto, @PathVariable final Long id) {
		var result = frameworkService.updateFramework(id, domainMapper.toEntity(requestDto));
		switch (result) {
			case UPDATED_NO_CONTENT:
				return ResponseEntity.noContent().build();
			case UPDATED_OK:
				return ResponseEntity.ok().build();
			default:
				return ResponseEntity.status(HttpStatus.CREATED).build();
		}
	}

	@DeleteMapping(path = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "The resource got deleted successfully."),
			@ApiResponse(code = 404, message = "The resource was not found for given id."),
	})
	public ResponseEntity<Void> deleteFramework(@PathVariable final Long id) {
		try {
			frameworkService.deleteFramework(id);
			return ResponseEntity.noContent().build();
		} catch (FrameworkNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private String resourceLocation(final Long id) {
		return FRAMEWORK_RESOURCE_LOCATION + "/" + id;
	}
}
