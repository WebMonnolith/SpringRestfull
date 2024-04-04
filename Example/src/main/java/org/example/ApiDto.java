package org.example;

import org.example.*;
import lombok.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.*;
import java.util.*;

@CompilationComponent
@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiDto extends DtoFrame {
	private String fname;

	private String index;

	private String age;

}
