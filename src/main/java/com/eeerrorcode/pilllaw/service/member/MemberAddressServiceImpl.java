package com.eeerrorcode.pilllaw.service.member;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.AddressDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAddress;
import com.eeerrorcode.pilllaw.repository.member.MemberAddressRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class MemberAddressServiceImpl implements MemberAddressService {
  @Autowired
  private MemberAddressRepository repository;

  @Override
  public Long register(AddressDto dto) {
    return repository.save(toEntity(dto)).getAddrno();
  }

  @Override
  public void remove(Long addrno) {
    repository.deleteById(addrno);
  }

  @Override
  public void modify(AddressDto dto) {
    repository.save(toEntity(dto));
  }

  @Override
  public List<AddressDto> getAddressesByMno(Long mno) {
    List<MemberAddress> addresses = repository.findByMemberMno(mno);
    return addresses.stream()
        .map(this::toDto) // Entity → DTO 변환
        .collect(Collectors.toList());
  }

  @Override
  public boolean isDuplicateAddress(AddressDto addressDto) {
    Optional<MemberAddress> existingAddress = repository
        .findByMember_MnoAndPostalCodeAndRoadAddressAndDetailAddressAndTel(
            addressDto.getMno(),
            addressDto.getPostalCode(),
            addressDto.getRoadAddress(),
            addressDto.getDetailAddress(),
            addressDto.getTel());
    return existingAddress.isPresent();
  }

  // AddressService.java (기존 addrno 조회 메서드 추가)
  @Override
  public Optional<Long> findExistingAddrno(AddressDto addressDto) {
    return repository.findByMember_MnoAndPostalCodeAndRoadAddressAndDetailAddressAndTel(
        addressDto.getMno(),
        addressDto.getPostalCode(),
        addressDto.getRoadAddress(),
        addressDto.getDetailAddress(),
        addressDto.getTel()).map(MemberAddress::getAddrno); //  기존 주소의 addrno 반환
  }

  @Override
  public Optional<AddressDto> getByMnoAndDefaultAddr(Long mno, boolean defaultAddress) {
    return repository.findByMnoAndDefaultAddr(mno, defaultAddress).isPresent()
        ? toOptionalDto(repository.findByMnoAndDefaultAddr(mno, defaultAddress).get())
        : Optional.empty();
  }

  @Override
  public List<AddressDto> getByMno(Long mno) {
    return repository.findByMno(mno).stream().map(this::toDto).toList();
  }
}
