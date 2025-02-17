package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.service.product.CategoryService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Log4j2
public class CategoryController {

  private CategoryService categoryService;
  

  @GetMapping("path")
  public String getMethodName(@RequestParam String param) {
      return new String();
  }
  
}