package usedmarket.usedmarket.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.category.domain.Category;
import usedmarket.usedmarket.domain.category.domain.CategoryRepository;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductDetailResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

//    public List<ProductAllResponseDto> findCategory(String name) {
//        return categoryRepository.findByName(name)
//                .map(ProductAllResponseDto::new)
//                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
//    }
}
