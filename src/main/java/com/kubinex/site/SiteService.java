package com.kubinex.site;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    private final SiteBlockRepository repo;

    public SiteService(SiteBlockRepository repo) {
        this.repo = repo;
    }

    public List<SiteBlockDto> getAll() {
        return repo.findAllByOrderBySortOrderAsc().stream()
                .map(SiteBlockDto::from)
                .toList();
    }

    public SiteBlockDto getBySlug(String slug) {
        return repo.findBySlug(slug).map(SiteBlockDto::from)
                .orElseThrow(() -> new RuntimeException("Block not found: " + slug));
    }

    public SiteBlockDto save(SiteBlockDto dto) {
        SiteBlock block = dto.id() != null
                ? repo.findById(dto.id()).orElseThrow()
                : new SiteBlock();
        return SiteBlockDto.from(repo.save(dto.update(block)));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
