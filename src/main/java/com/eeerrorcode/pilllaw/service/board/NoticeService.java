package com.eeerrorcode.pilllaw.service.board;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eeerrorcode.pilllaw.dto.board.NoticeDto;
import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.member.Member;

public interface NoticeService {
  void register(NoticeDto dto);

  void delete(Long nno);

  void modify(NoticeDto dto);

  Notice view(Long nno);

  void realView(Long nno);

  Page<NoticeDto> showList(Pageable pageable);

  default Notice toEntity(NoticeDto dto){
    Notice notice = Notice
    .builder()
      .nno(dto.getNno())
      .content(dto.getContent())
      .title(dto.getTitle())
      .count(dto.getCount())
      .member(Member.builder().mno(dto.getMno()).build())
      .files(null)
    .build();

    if(dto.getFileDtos() != null){
      dto.getFileDtos().forEach(fileDto ->{
        File file = File
        .builder()
          .uuid(fileDto.getUuid())
          .origin(fileDto.getPath())
          .path(fileDto.getPath())
          .fname(fileDto.getFname())
          .mime(fileDto.getMime())
          .size(fileDto.getSize())
          .ext(fileDto.getExt())
          .url(fileDto.getUrl())
          .notice(notice)
          .type(FileType.REVIEW)
        .build();
        notice.getFiles().add(file);
      });
    }
    return notice;
  }

  default NoticeDto toDto(Notice notice){
    List<FileDto> fileDtos = notice.getFiles().stream()
      .map(file -> new FileDto(
        file.getUuid(),
        file.getOrigin(),
        file.getPath(),
        file.getFname(),
        file.getMime(),
        file.getSize(),
        file.getExt(),
        file.getUrl(),
        (file.getProductReview() != null) ? file.getProductReview().getPrno() : null,
        (file.getProductDetail() != null) ? file.getProductDetail().getPdno() : null,
        (file.getNotice() != null) ? file.getNotice().getNno() : null,
        file.getType(),
        file.getRegDate(),
        file.getModDate()
      ))
      .toList();

    NoticeDto noticeDto = NoticeDto
    .builder()
      .content(notice.getContent())
      .mno(notice.getMember().getMno())
      .nno(notice.getNno())
      .count(notice.getCount())
      .title(notice.getTitle())
      .fileDtos(fileDtos)
    .build();
    
    return noticeDto;
  }
}
