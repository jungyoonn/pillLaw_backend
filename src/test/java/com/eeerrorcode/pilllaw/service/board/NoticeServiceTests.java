package com.eeerrorcode.pilllaw.service.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.board.NoticeDto;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.board.NoticeRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoticeServiceTests {
  
  @Autowired
  private NoticeRepository noticeRepository;

  @Autowired
  private NoticeService noticeService;

  @Test
  public void testExist(){
    log.info(noticeRepository);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testRegister(){
    // Notice newNotice = Notice
    // .builder()
    //   .content("Test Notice 입니다.")
    //   .member(Member.builder().mno(7L).build())
    //   .title("Test Notice 제목입니다.")
    // .build();
    // noticeRepository.save(newNotice);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testView(){
    noticeService.view(2L);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    noticeService.delete(2L);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testShowList(){

    Pageable pageable = PageRequest.of(0, 5, Sort.by("regDate").descending());
    Page<NoticeDto> result = noticeService.showList(pageable);

    result.getContent().forEach(notice -> 
    System.out.println("공지사항 제목: " + notice.getTitle()));
    
  }

}
