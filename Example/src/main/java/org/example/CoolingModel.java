package org.example;

import org.framework.web.core.templates.*;

import java.util.*;

@lombok.EqualsAndHashCode(callSuper=true)
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
@jakarta.persistence.Entity
@jakarta.persistence.Table(name="t_cooling")
public class CoolingModel extends ModelFrame<UUID> {
		@jakarta.persistence.Id
	@jakarta.persistence.GeneratedValue(strategy=jakarta.persistence.GenerationType.UUID)
	private UUID id;
	private int testField;

	private double testField2;

}
