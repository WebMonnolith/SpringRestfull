package org.example.repository;

import org.example.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import java.util.*;
import org.springframework.stereotype.Repository;

@CompilationComponent
@Repository
public interface ApiRepository extends TRepo<ApiModel, UUID> {
}
