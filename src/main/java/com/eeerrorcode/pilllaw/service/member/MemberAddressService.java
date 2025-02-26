package com.eeerrorcode.pilllaw.service.member;

import com.eeerrorcode.pilllaw.dto.member.AddressDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.MemberAddress;

public interface MemberAddressService {
  Long register(AddressDto dto);

  void remove(Long addrno);

  void modify(AddressDto dto);

  default AddressDto toDto(MemberAddress address) {
    AddressDto dto = AddressDto.builder()
      .addrno(address.getAddrno())
      .recipient(address.getRecipient())
      .tel(address.getTel())
      .postalCode(address.getPostalCode())
      .roadAddress(address.getRoadAddress())
      .detailAddress(address.getDetailAddress())
      .defaultAddr(address.isDefaultAddr())
      .mno(address.getMember().getMno())
      .build();

    return dto;
  }

  default MemberAddress toEntity(AddressDto dto) {
    MemberAddress address = MemberAddress.builder()
      .addrno(dto.getAddrno())
      .recipient(dto.getRecipient())
      .tel(dto.getTel())
      .postalCode(dto.getPostalCode())
      .roadAddress(dto.getRoadAddress())
      .detailAddress(dto.getDetailAddress())
      .defaultAddr(dto.isDefaultAddr())
      .member(Member.builder().mno(dto.getMno()).build())
      .build();

    return address;
  }
}
