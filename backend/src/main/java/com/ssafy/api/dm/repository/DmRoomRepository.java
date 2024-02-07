package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DmRoomRepository extends JpaRepository<DMRoom, Long> {

//  @Query("SELECT d FROM DMRoom d WHERE d.coach = :coachId and d.coame = :coameId ")
//  DMRoom findByCoachAndCoame(Member coachId, Member coameId);

  List<DMRoom> findAllByIdIn(List<Long> roomIdList);

  @Query("SELECT d FROM DMRoom d " +
      "WHERE d.member1 IN (:member1, :member2)" +
      "  AND d.member2 IN (:member1, :member2)")
  DMRoom findByMembers(Member member1, Member member2);

  @Query("SELECT d FROM DMRoom d " +
      "WHERE :memberId IN (d.member1, d.member2)")
  List<DMRoom> findByMemberId(long memberId);
//
//  @Modifying
//  @Query("DELETE FROM DMRoom e WHERE e.dmroomId = :dmroomId AND (e.coach = :member or e.coame = :member)")
//  void deleteByDmroomIdAndMember(int dmroomId, Member member);

}
