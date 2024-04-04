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
public class FileController implements TControllerEntityResponse<Integer, FileRequest, FileResponse> {
	private final FileService fileService;
	@Override
	@PostMapping
	public ResponseEntity<Integer> insertEntity(FileRequest filerequest) {
		 return ResponseEntity.ok(fileService.insert(filerequest));
	}
	@Override
	@GetMapping
	public ResponseEntity<List<FileRequest>> getAllEntities() {
		 return ResponseEntity.ok(fileService.getAll());
	}
	@Override
	@DeleteMapping
	public ResponseEntity<Boolean> removeEntityById(Integer id) {
		 return ResponseEntity.ok(fileService.removeById(id));
	}
	@Override
	@PutMapping
	public ResponseEntity<Boolean> updateEntity(Integer id, FileResponse fileresponse) {
		 return ResponseEntity.ok(fileService.update(id, fileresponse));
	}
}
