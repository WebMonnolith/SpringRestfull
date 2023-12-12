package org.example.service;

import org.framework.web.core.templates.*;

import java.util.*;

@lombok.Data
@lombok.AllArgsConstructor
@org.springframework.stereotype.Service
public class CoolingService implements ServiceTemplate<UUID> {
	private final org.example.repository.CoolingRepository repository;
}
