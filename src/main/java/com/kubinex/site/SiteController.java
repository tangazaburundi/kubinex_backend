package com.kubinex.site;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SiteController {

    private final SiteService service;

    public SiteController(SiteService service) {
        this.service = service;
    }

    @GetMapping("/public/site")
    public List<SiteBlockDto> getPublic() {
        return service.getAll();
    }

    @GetMapping("/public/site/{slug}")
    public SiteBlockDto getBySlug(@PathVariable String slug) {
        return service.getBySlug(slug);
    }

    @GetMapping("/admin/site")
    public List<SiteBlockDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/admin/site/{id}")
    public SiteBlockDto update(@PathVariable Long id, @RequestBody SiteBlockDto dto) {
        return service.save(new SiteBlockDto(id, dto.slug(), dto.title(), dto.subtitle(),
                dto.content(), dto.imageUrl(), dto.extra(), dto.sortOrder()));
    }

    @PostMapping("/admin/site")
    public SiteBlockDto create(@RequestBody SiteBlockDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/admin/site/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
