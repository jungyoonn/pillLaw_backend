package com.eeerrorcode.pilllaw.service.product;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eeerrorcode.pilllaw.PilllawApplication;
import com.eeerrorcode.pilllaw.dto.product.CategoryDto;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest(classes = {PilllawApplication.class, CategoryServiceImpl.class})
@Log4j2
@Transactional
public class CategoryServiceTests {

  @Autowired
  private CategoryService categoryService;

  @Test
  public void testListCategoryByType() {
    CategoryType type = CategoryType.BIOACTIVE;
    List<CategoryDto> bioactiveCategories = categoryService.listCategoryByType(type);

    log.info("BIOACTIVE 카테고리 개수: " + bioactiveCategories.size());
    bioactiveCategories.forEach(dto -> log.info(dto.getCname()));

    CategoryType type2 = CategoryType.NUTRIENT;
    List<CategoryDto> nutrientCategories = categoryService.listCategoryByType(type2);

    log.info("NUTRIENT 카테고리 개수: " + nutrientCategories.size());
    nutrientCategories.forEach(dto -> log.info(dto.getCname()));

    assertFalse(bioactiveCategories.isEmpty(), "BIOACTIVE 카테고리가 조회되지 않음");
    assertFalse(nutrientCategories.isEmpty(), "NUTRIENT 카테고리가 조회되지 않음");
  }

}
