package org.example.repository;

import org.example.*;
import org.springframework.stereotype.Repository;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@UpdateComponent
@CompilationComponent
@Repository
public interface ApiRepository extends TRepo<ApiModel, Integer> {
}
