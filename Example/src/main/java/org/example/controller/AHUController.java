package org.example.controller;

import org.framework.web.core.templates.*;

import java.util.*;

@lombok.Data
@lombok.AllArgsConstructor
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/tools/calculation_api/AHU")
public class AHUController implements ControllerTemplate<UUID> {
	private final org.example.service.AHUService serviceAHU;
}
