package com.saybetter.domain.member.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.member.domain.Member;

public interface MemberWriteRepository extends JpaRepository<Member, Long> {
}
