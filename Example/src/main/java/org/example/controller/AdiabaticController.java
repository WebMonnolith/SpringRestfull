package org.example.controller;

import org.framework.web.core.templates.*;

import java.util.*;

@lombok.Data
@lombok.AllArgsConstructor
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/tools/calculation_api/adiabatic")
public class AdiabaticController implements ControllerTemplate<UUID> {
	private final org.example.service.AdiabaticService serviceAdiabatic;
}
