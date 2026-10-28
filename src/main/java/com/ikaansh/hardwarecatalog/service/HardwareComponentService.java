package com.ikaansh.hardwarecatalog.service;

import com.ikaansh.hardwarecatalog.dto.request.HardwareComponentRequest;
import com.ikaansh.hardwarecatalog.dto.response.HardwareComponentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HardwareComponentService {
    HardwareComponentResponse createComponent(HardwareComponentRequest request);
    HardwareComponentResponse getComponentById(Long id);
    Page<HardwareComponentResponse> getAllComponents(String category, String search, Pageable pageable);
    HardwareComponentResponse updateComponent(Long id, HardwareComponentRequest request);
    void deleteComponent(Long id);
}