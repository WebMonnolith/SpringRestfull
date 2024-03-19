package org.example.controller;

import org.example.*;
import org.example.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@UpdateComponent
@CompilationComponent
@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/endpoint")
public class ApiController implements TControllerEntityResponse<UUID, ApiDto, ApiModel> {
	private final ApiService apiService;
	@Override
	@PostMapping
	public ResponseEntity<Integer> insertEntity(ApiDto apidto) {
		 return ResponseEntity.ok(apiService.insert(apidto));
	}
	@Override
	@GetMapping
	public ResponseEntity<List<ApiDto>> getAllEntities() {
		 return ResponseEntity.ok(apiService.getAll());
	}
	@Override
	@DeleteMapping
	public ResponseEntity<Boolean> removeEntityById(UUID id) {
		 return ResponseEntity.ok(apiService.removeById(id));
	}
	@Override
	@PutMapping
	public ResponseEntity<Boolean> updateEntity(UUID id, ApiModel apimodel) {
		 return ResponseEntity.ok(apiService.update(id, apimodel));
	}
}