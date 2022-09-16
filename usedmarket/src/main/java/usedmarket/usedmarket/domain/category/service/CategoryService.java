package usedmarket.usedmarket.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.category.domain.Category;
import usedmarket.usedmarket.domain.category.domain.CategoryRepository;
import usedmarket.usedmarket.domain.category.presentation.dto.request.CategoryRequestDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CategoryRequestDto requestDto) {
        //이 메서드는 나중에 삭제해야합니다.
        categoryRepository.save(requestDto.toEntity());
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
    }
}
