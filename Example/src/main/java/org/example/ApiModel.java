package org.example;

import org.example.*;
import org.restframework.web.core.templates.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import java.util.*;
import lombok.*;
import jakarta.persistence.*;

@CompilationComponent
@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="test_table")
public class ApiModel extends ModelFrame<UUID> {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	private String fname;

	private String index;

}
