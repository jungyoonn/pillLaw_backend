package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.board.NoticeDto;
import com.eeerrorcode.pilllaw.service.board.NoticeService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@Log4j2
@RequestMapping("api/v1/notice")
@Transactional
public class NoticeController {

  @Autowired
  private NoticeService noticeService;

  // 포스트맨 통과!!
  @GetMapping("list")
  public ResponseEntity<Page<NoticeDto>> getList(@PageableDefault(size = 10, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
    log.info("Notice List :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    Page<NoticeDto> returnList = noticeService.showList(pageable);
    return ResponseEntity.ok(returnList);
  }

  // 포스트맨 통과!!
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody NoticeDto dto) {
    log.info("Notice register :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    noticeService.register(dto);
    return ResponseEntity.ok(dto.getTitle());
  }
  

  // 포스트맨 통과!!
  @PutMapping("/{nno}")
  public ResponseEntity<?> modify(@RequestBody NoticeDto dto) {
    log.info("Notice register :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    noticeService.modify(dto);
    log.info(dto.toString());
    return ResponseEntity.ok(dto);
  }
  


  // 포스트맨 통과!!
  @DeleteMapping(value = "/{nno}")
  public ResponseEntity<?> remove(@PathVariable("nno") Long nno){
    log.info("Notice remove :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    noticeService.delete(nno);
    return ResponseEntity.ok(nno + "deleted");
  }
  
  
}
