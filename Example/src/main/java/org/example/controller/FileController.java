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
public class FileController implements TControllerEntityResponse<Integer, FileDto, FileModel> {
	private final FileService fileService;
	@Override
	@PostMapping
	public ResponseEntity<Integer> insertEntity(FileDto filedto) {
		 return ResponseEntity.ok(fileService.insert(filedto));
	}
	@Override
	@GetMapping
	public ResponseEntity<List<FileDto>> getAllEntities() {
		 return ResponseEntity.ok(fileService.getAll());
	}
	@Override
	@DeleteMapping
	public ResponseEntity<Boolean> removeEntityById(Integer id) {
		 return ResponseEntity.ok(fileService.removeById(id));
	}
	@Override
	@PutMapping
	public ResponseEntity<Boolean> updateEntity(Integer id, FileModel filemodel) {
		 return ResponseEntity.ok(fileService.update(id, filemodel));
	}
}
