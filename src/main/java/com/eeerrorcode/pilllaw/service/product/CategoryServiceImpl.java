package com.eeerrorcode.pilllaw.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.CategoryDto;
import com.eeerrorcode.pilllaw.entity.product.Category;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.repository.product.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

  private final CategoryRepository categoryRepository;

  // 테스트 완료!
  @Override
  @Transactional
  public List<CategoryDto> listCategoryByType(CategoryType type) {
      List<Category> categoryList = categoryRepository.findByType(type);
      if (categoryList.isEmpty()) {
          throw new RuntimeException("타입 없음 에러::::" + type);
      }
      return categoryList.stream()
          .map(CategoryDto::new)
          .toList();
  }
  

}
