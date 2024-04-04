package org.example.repository;

import org.example.*;
import org.springframework.stereotype.Repository;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@CompilationComponent
@Repository
public interface FileRepository extends TRepo<FileModel, Integer> {
}
