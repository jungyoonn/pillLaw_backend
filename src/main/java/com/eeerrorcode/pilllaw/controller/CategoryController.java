package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.service.product.CategoryService;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@Log4j2
@RequestMapping("api/v1/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;
  

  @GetMapping("/list/bioactive")
  public ResponseEntity<List<?>> getBioActivity() {
    List<?> categoryTypes = categoryService.listCategoryByType(CategoryType.BIOACTIVE);
    return ResponseEntity.ok(categoryTypes);
  }

  @GetMapping("/list/nutrient")
  public ResponseEntity<List<?>> getNutrient() {
    List<?> categoryTypes = categoryService.listCategoryByType(CategoryType.NUTRIENT);
    return ResponseEntity.ok(categoryTypes);
  }
  
}