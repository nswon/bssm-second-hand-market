package usedmarket.usedmarket.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usedmarket.usedmarket.domain.category.domain.Category;
import usedmarket.usedmarket.domain.category.domain.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
