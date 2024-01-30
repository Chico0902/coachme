package com.ssafy.api.member.repository;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.status.ElevateStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByStringId(String stringId);

    @Query(value = "SELECT m FROM Member m join fetch m.portfolio " +
                        "WHERE m.elevateStatus = ?1")
    List<Member> findByElevateStatusWithPortfolio(ElevateStatus elevateStatus);
}