package com.ikaansh.hardwarecatalog.controller;

import com.ikaansh.hardwarecatalog.dto.request.HardwareComponentRequest;
import com.ikaansh.hardwarecatalog.dto.response.HardwareComponentResponse;
import com.ikaansh.hardwarecatalog.service.HardwareComponentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/components")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows smooth local frontend/Postman integration
public class HardwareComponentController {

    private final HardwareComponentService componentService;

    @PostMapping
    public ResponseEntity<HardwareComponentResponse> createComponent(
            @Valid @RequestBody HardwareComponentRequest request) {
        HardwareComponentResponse response = componentService.createComponent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwareComponentResponse> getComponentById(@PathVariable Long id) {
        HardwareComponentResponse response = componentService.getComponentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<HardwareComponentResponse>> getAllComponents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HardwareComponentResponse> response = componentService.getAllComponents(category, search, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardwareComponentResponse> updateComponent(
            @PathVariable Long id,
            @Valid @RequestBody HardwareComponentRequest request) {
        HardwareComponentResponse response = componentService.updateComponent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
}