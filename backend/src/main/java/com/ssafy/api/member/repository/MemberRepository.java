package com.ssafy.api.member.repository;

import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

  Optional<Member> findByMemberId(String memberId);

  Member findById(Long id);

  Member findByMemberIdAndEmail(String memberId, String email);

}