package com.ssafy.api.member.repository;

import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

  Optional<Member> findByMemberId(String memberId);

  List<Member> findByIdIn(List<Long> idList);

  Member findById(Long id);

  @Query("select m from Member m where elevation = 1")
  List<Member> findElevationMember();

  Member findByMemberIdAndEmail(String memberId, String email);

}