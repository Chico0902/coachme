package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DmRoomRepository extends JpaRepository<DMRoom, Long> {

  List<DMRoom> findAllByIdIn(List<Long> roomIdList);

  @Query("SELECT d FROM DMRoom d " +
      "WHERE d.member1 IN (:member1, :member2)" +
      "  AND d.member2 IN (:member1, :member2)")
  DMRoom findByMembers(Member member1, Member member2);

  @Query("SELECT d FROM DMRoom d " +
      "WHERE :memberId IN (d.member1.longId, d.member2.longId)")
  List<DMRoom> findByMemberId(long memberId);


}
