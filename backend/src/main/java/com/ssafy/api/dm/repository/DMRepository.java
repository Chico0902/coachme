package com.ssafy.api.dm.repository;

import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DMRepository extends JpaRepository<DM, Long> {
  List<DM> findByDmRoom(DMRoom room);
}