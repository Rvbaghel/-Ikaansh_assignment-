package com.ikaansh.hardwarecatalog.repository;

import com.ikaansh.hardwarecatalog.entity.HardwareComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HardwareComponentRepository extends JpaRepository<HardwareComponent, Long>, JpaSpecificationExecutor<HardwareComponent> {

    // Filtering by category (case-insensitive)
    List<HardwareComponent> findByCategoryIgnoreCase(String category);

    // Searching by name (case-insensitive containing)
    List<HardwareComponent> findByNameContainingIgnoreCase(String name);

    // Combined search and filter with pagination & sorting support
    @Query("SELECT h FROM HardwareComponent h WHERE " +
            "(:category IS NULL OR LOWER(h.category) = LOWER(:category)) AND " +
            "(:search IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<HardwareComponent> findByCategoryAndSearch(
            @Param("category") String category,
            @Param("search") String search,
            Pageable pageable
    );
}