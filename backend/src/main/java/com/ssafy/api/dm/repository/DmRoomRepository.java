package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DmRoomRepository extends JpaRepository<DMRoom, Long> {

//  @Query("SELECT d FROM DMRoom d WHERE d.coach = :coachId and d.coame = :coameId ")
  DMRoom findByCoachAndCoame(Member coachId, Member coameId);

  List<DMRoom> findAllByIdIn(List<Long> roomIdList);
//
//  List<DMRoom> findByCoachOrCoame(Member coame, Member coach);
//
//  @Modifying
//  @Query("DELETE FROM DMRoom e WHERE e.dmroomId = :dmroomId AND (e.coach = :member or e.coame = :member)")
//  void deleteByDmroomIdAndMember(int dmroomId, Member member);

}
