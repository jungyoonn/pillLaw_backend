package com.eeerrorcode.pilllaw.service.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.board.NoticeDto;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.repository.board.NoticeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService{

  @Autowired
  private final NoticeRepository noticeRepository;

  // 테스트 성공!!
  @Override
  public void delete(Long nno) {
    noticeRepository.deleteById(nno);
  }


  // 테스트 성공!!
  @Override
  public void modify(NoticeDto dto) {
    // Notice returnNotice = noticeRepository.findById(dto.getNno()).orElseThrow(()->new IllegalArgumentException("Notice Not found"));
    
    // List<File> newFiles = dto.getFileDtos().stream()
    // .map(a -> File.builder()
    //   .uuid(a.getUuid())
    //   .origin(a.getOrigin())
    //   .path(a.getPath())
    //   .fname(a.getFname())
    //   .mime(a.getMime())
    //   .size(a.getSize())
    //   .ext(a.getExt())
    //   .url(a.getUrl())
    //   .notice(returnNotice)
    //   .type(FileType.NOTICE)
    //   .build())
    // .toList();

    // returnNotice.updateFiles(newFiles);
    // returnNotice.updateNotice(dto.getContent());
    // returnNotice.updateNotice(dto.getTitle());
    noticeRepository.save(toEntity(dto));
  }


  // 테스트 성공!!
  @Override
  public void register(NoticeDto dto) {
    Notice returnNotice = toEntity(dto);
    noticeRepository.save(returnNotice);
  }


  // 테스트 성공!!
  @Override
  public Notice view(Long nno) {
    Notice returnNotice = noticeRepository.findById(nno).get();
    return returnNotice;
  }

  // 테스트 성공!!
  @Override
  public Page<NoticeDto> showList(Pageable pageable) {
    Page<NoticeDto> returnList = noticeRepository.findAll(pageable).map(this::toDto);
    return returnList;
  }
  
}
