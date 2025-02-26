package com.eeerrorcode.pilllaw.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.AddressDto;
import com.eeerrorcode.pilllaw.repository.member.MemberAddressRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class MemberAddressServiceImpl implements MemberAddressService{
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
  
}
