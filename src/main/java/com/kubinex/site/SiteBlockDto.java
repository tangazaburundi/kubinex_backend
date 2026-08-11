package com.kubinex.site;

import jakarta.validation.constraints.NotBlank;

public record SiteBlockDto(
    Long id,
    @NotBlank String slug,
    String title,
    String subtitle,
    String content,
    String imageUrl,
    String extra,
    int sortOrder
) {
    static SiteBlockDto from(SiteBlock b) {
        return new SiteBlockDto(b.getId(), b.getSlug(), b.getTitle(), b.getSubtitle(),
                b.getContent(), b.getImageUrl(), b.getExtra(), b.getSortOrder());
    }

    SiteBlock update(SiteBlock b) {
        b.setSlug(slug);
        b.setTitle(title);
        b.setSubtitle(subtitle);
        b.setContent(content);
        b.setImageUrl(imageUrl);
        b.setExtra(extra);
        b.setSortOrder(sortOrder);
        return b;
    }
}
