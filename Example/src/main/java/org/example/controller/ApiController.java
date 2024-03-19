package org.example.controller;

import org.example.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import java.util.*;
import org.example.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@CompilationComponent
@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/endpoint")
public class ApiController implements TControllerCRUD<UUID, ApiDto, ApiModel> {
	private final ApiService apiService;
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public int insertEntity(ApiDto apidto) {
		 return apiService.insert(apidto);
	}
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ApiDto> getAllEntities() {
		 return apiService.getAll();
	}
	@Override
	@DeleteMapping
	@ResponseStatus(HttpStatus.FOUND)
	public boolean removeEntityById(UUID id) {
		 return apiService.removeById(id);
	}
	@Override
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public boolean updateEntity(UUID id, ApiModel apimodel) {
		 return apiService.update(id, apimodel);
	}
}
