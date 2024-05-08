package org.example.service;

import org.example.*;
import org.example.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@CompilationComponent
@Data
@RequiredArgsConstructor
@Service
public class ApiService implements TServiceCRUD<Integer, ApiDto, ApiModel> {
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
	public boolean removeById(Integer id) {
		 return false;
	}
	@Override
	public boolean update(Integer id, ApiModel apimodel) {
		 return false;
	}
}
