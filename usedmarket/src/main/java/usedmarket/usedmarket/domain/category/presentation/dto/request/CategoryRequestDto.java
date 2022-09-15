package usedmarket.usedmarket.domain.category.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.category.domain.Category;

@Getter
public class CategoryRequestDto {

    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
