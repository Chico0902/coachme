package com.ssafy.api.member.repository;

import com.ssafy.db.entity.File;
import com.ssafy.db.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {

  File findByMemberAndType(Member member, String type);


}
