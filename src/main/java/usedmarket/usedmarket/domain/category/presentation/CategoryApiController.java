package usedmarket.usedmarket.domain.category.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.category.presentation.dto.request.CategoryRequestDto;
import usedmarket.usedmarket.domain.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/new")
    public void createCategory(@RequestBody CategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
    }
}
