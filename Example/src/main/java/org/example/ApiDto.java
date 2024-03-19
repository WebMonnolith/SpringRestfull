package org.example;

import org.example.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import java.util.*;
import lombok.*;

@CompilationComponent
@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiDto extends DtoFrame {
	private String fname;

	private String index;

}
