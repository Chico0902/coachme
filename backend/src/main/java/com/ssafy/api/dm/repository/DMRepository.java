package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DMRepository extends JpaRepository<DM, Long> {
  List<DM> findByDmRoom(DMRoom room);

  @Query("SELECT e FROM DM e WHERE e.dmRoom.id = :roomId ORDER BY e.id DESC LIMIT 1 ")
  DM findByLastDm(long roomId);
}