package com.eeerrorcode.pilllaw.service.product;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.product.CategoryDto;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;

public interface CategoryService {
  
  List<CategoryDto> listCategoryByType(CategoryType type);

}
