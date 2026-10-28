package com.ikaansh.hardwarecatalog.service;

import com.ikaansh.hardwarecatalog.dto.request.HardwareComponentRequest;
import com.ikaansh.hardwarecatalog.dto.response.HardwareComponentResponse;
import com.ikaansh.hardwarecatalog.entity.HardwareComponent;
import com.ikaansh.hardwarecatalog.exception.ResourceNotFoundException;
import com.ikaansh.hardwarecatalog.repository.HardwareComponentRepository;
import com.ikaansh.hardwarecatalog.service.impl.HardwareComponentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HardwareComponentServiceTest {

    @Mock
    private HardwareComponentRepository repository;

    @InjectMocks
    private HardwareComponentServiceImpl service;

    private HardwareComponent sampleComponent;
    private HardwareComponentRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleComponent = HardwareComponent.builder()
                .id(1L)
                .name("Intel Core i7-14700K")
                .category("Processor")
                .specifications("20 Cores, 28 Threads, up to 5.6 GHz")
                .price(new BigDecimal("409.99"))
                .stockQuantity(10)
                .build();

        sampleRequest = HardwareComponentRequest.builder()
                .name("Intel Core i7-14700K")
                .category("Processor")
                .specifications("20 Cores, 28 Threads, up to 5.6 GHz")
                .price(new BigDecimal("409.99"))
                .stockQuantity(10)
                .build();
    }

    @Test
    @DisplayName("createComponent should save and return component response")
    void createComponent_Success() {
        when(repository.save(any(HardwareComponent.class))).thenReturn(sampleComponent);

        HardwareComponentResponse response = service.createComponent(sampleRequest);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Intel Core i7-14700K");
        verify(repository, times(1)).save(any(HardwareComponent.class));
    }

    @Test
    @DisplayName("getComponentById should return component when ID exists")
    void getComponentById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleComponent));

        HardwareComponentResponse response = service.getComponentById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Intel Core i7-14700K");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getComponentById should throw ResourceNotFoundException when ID does not exist")
    void getComponentById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getComponentById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Hardware component not found with ID: 99");

        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("deleteComponent should delete when ID exists")
    void deleteComponent_Success() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteComponent(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteComponent should throw ResourceNotFoundException when ID does not exist")
    void deleteComponent_NotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteComponent(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Hardware component not found with ID: 99");

        verify(repository, never()).deleteById(anyLong());
    }
}