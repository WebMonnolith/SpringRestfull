package org.example.service;

import org.framework.web.core.templates.*;

import java.util.*;

@lombok.Data
@lombok.AllArgsConstructor
@org.springframework.stereotype.Service
public class AHUService implements ServiceTemplate<UUID> {
	private final org.example.repository.AHURepository repository;
}
