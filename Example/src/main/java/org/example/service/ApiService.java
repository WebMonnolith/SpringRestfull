package org.example.service;

import org.example.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import java.util.*;
import org.example.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;

@CompilationComponent
@Data
@AllArgsConstructor
@Service
public class ApiService implements TServiceCRUD<UUID, ApiDto, ApiModel> {
	private final ApiRepository repository;
	@Override
	public int insert(ApiDto apidto) {
		 return 0;
	}
	@Override
	public List<ApiDto> getAll() {
		 return null;
	}
	@Override
	public boolean removeById(UUID id) {
		 return false;
	}
	@Override
	public boolean update(UUID id, ApiModel apimodel) {
		 return false;
	}
}
