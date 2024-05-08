package org.example.controller;

import org.example.*;
import org.example.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@CompilationComponent
@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/endpoint")
public class ApiController implements TControllerEntityResponse<Integer, ApiDto, ApiModel> {
	private final ApiService apiService;
	@Override
	@PostMapping
	public ResponseEntity<Integer> insertEntity(ApiDto apidto) {
		throw new NullPointerException("hello there");
//		return ResponseEntity.ok(apiService.insert(apidto));
	}
	@Override
	@GetMapping
	public ResponseEntity<List<ApiDto>> getAllEntities() {
		throw new NullPointerException("");
//		 return ResponseEntity.ok(apiService.getAll());
	}
	@Override
	@DeleteMapping
	public ResponseEntity<Boolean> removeEntityById(Integer id) {
		 return ResponseEntity.ok(apiService.removeById(id));
	}
	@Override
	@PutMapping
	public ResponseEntity<Boolean> updateEntity(Integer id, ApiModel apimodel) {
		 return ResponseEntity.ok(apiService.update(id, apimodel));
	}
}
