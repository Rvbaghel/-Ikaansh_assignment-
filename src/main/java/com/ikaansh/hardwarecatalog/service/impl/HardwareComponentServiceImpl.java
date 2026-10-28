package com.ikaansh.hardwarecatalog.service.impl;

import com.ikaansh.hardwarecatalog.dto.request.HardwareComponentRequest;
import com.ikaansh.hardwarecatalog.dto.response.HardwareComponentResponse;
import com.ikaansh.hardwarecatalog.entity.HardwareComponent;
import com.ikaansh.hardwarecatalog.exception.ResourceNotFoundException;
import com.ikaansh.hardwarecatalog.repository.HardwareComponentRepository;
import com.ikaansh.hardwarecatalog.repository.HardwareComponentSpecification;
import com.ikaansh.hardwarecatalog.service.HardwareComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HardwareComponentServiceImpl implements HardwareComponentService {

    private final HardwareComponentRepository repository;

    @Override
    @Transactional
    public HardwareComponentResponse createComponent(HardwareComponentRequest request) {
        HardwareComponent component = HardwareComponent.builder()
                .name(request.getName().trim())
                .category(request.getCategory().trim())
                .specifications(request.getSpecifications())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        HardwareComponent saved = repository.save(component);
        return HardwareComponentResponse.fromEntity(saved);
    }

    @Override
    public HardwareComponentResponse getComponentById(Long id) {
        HardwareComponent component = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hardware component not found with ID: " + id));
        return HardwareComponentResponse.fromEntity(component);
    }

    @Override
    public Page<HardwareComponentResponse> getAllComponents(String category, String search, Pageable pageable) {
        Specification<HardwareComponent> spec = HardwareComponentSpecification.filterBy(category, search);
        return repository.findAll(spec, pageable).map(HardwareComponentResponse::fromEntity);
    }

    @Override
    @Transactional
    public HardwareComponentResponse updateComponent(Long id, HardwareComponentRequest request) {
        HardwareComponent component = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hardware component not found with ID: " + id));

        component.setName(request.getName().trim());
        component.setCategory(request.getCategory().trim());
        component.setSpecifications(request.getSpecifications());
        component.setPrice(request.getPrice());
        component.setStockQuantity(request.getStockQuantity());

        HardwareComponent updated = repository.save(component);
        return HardwareComponentResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public void deleteComponent(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Hardware component not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}