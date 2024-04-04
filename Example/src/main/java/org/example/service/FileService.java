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
@AllArgsConstructor
@Service
public class FileService implements TServiceCRUD<Integer, FileDto, FileModel> {
	private final FileRepository repository;
	@Override
	public int insert(FileDto filedto) {
		 return 0;
	}
	@Override
	public List<FileDto> getAll() {
		 return null;
	}
	@Override
	public boolean removeById(Integer id) {
		 return false;
	}
	@Override
	public boolean update(Integer id, FileModel filemodel) {
		 return false;
	}
}
