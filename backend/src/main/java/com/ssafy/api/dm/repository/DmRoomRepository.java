package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DmRoomRepository extends JpaRepository<DMRoom, Integer> {

  DMRoom findByCoachAndCoame(Member coameId, Member coachId);

  List<DMRoom> findByDmroomIdIn(List<Integer> roomIdList);

  List<DMRoom> findByCoachOrCoame(Member coame, Member coach);

  @Modifying
  @Query("DELETE FROM DMRoom e WHERE e.dmroomId = :dmroomId AND (e.coach = :member or e.coame = :member)")
  void deleteByDmroomIdAndMember(int dmroomId, Member member);

}
