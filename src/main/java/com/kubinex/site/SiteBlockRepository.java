package com.kubinex.site;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteBlockRepository extends JpaRepository<SiteBlock, Long> {
    Optional<SiteBlock> findBySlug(String slug);
    List<SiteBlock> findAllByOrderBySortOrderAsc();
}
