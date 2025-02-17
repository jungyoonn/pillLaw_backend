package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@Log4j2
@RequestMapping("api/v1/notice")
public class NoticeController {


  @GetMapping("path")
  public String getMethodName(@RequestParam String param) {
      return new String();
  }

  @PostMapping("path")
  public String postMethodName(@RequestBody String entity) {
      //TODO: process POST request
      
      return entity;
  }

  @PutMapping("path/{id}")
  public String putMethodName(@PathVariable String id, @RequestBody String entity) {
      //TODO: process PUT request
      
      return entity;
  }
  
  @DeleteMapping()
  public String dd(){
    return null;
  }
  
  
}
