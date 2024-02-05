package com.ssafy.api.coaching.service;

import com.ssafy.api.coach.dto.CoachesCoachingsResponseDto;
import com.ssafy.api.coaching.dto.CoachingInfoChangeRequestDto;
import com.ssafy.api.coaching.dto.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.GetOneCoachingResponseDto;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachingService {

  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final CategoryRepository categoryRepository;

  /**
   * 코칭 정보를 받아서 코칭 등록
   */
  public void createCoaching(Long longId, CreateCoachingRequestDto dto) {

    // 1. DB에서 멤버를 찾아온다
    Member memberInDB = memberRepository.getReferenceById(longId);
    Category main = categoryRepository.findByName(dto.getMain());
    Category sub = categoryRepository.findByName(dto.getSub());

    // 2. 새로운 코칭을 만든다.
    Coaching newCoaching = Coaching.builder()
        .name(dto.getName())
        .htmlDocs(dto.getHtmlDocs())
        .mainCategory(main)
        .subCategory(sub)
        .summary(dto.getSummary()).build();

//   newCoaching.categorize(main, sub);
    if(sub == null) {
      newCoaching.addOneCategory(main);
      main.addOneCategory(newCoaching);
    } else {
      newCoaching.addBothCategories(main, sub);
      sub.addCategoryList(newCoaching);
    }

    // 3. 새롭게 만든 코칭에 맴버를 등록한다.
    newCoaching.registCoaching(memberInDB);
  }

  /**
   * 해당 코치의 코칭 1개 조회
   */
  @Transactional(readOnly = true)
  public GetOneCoachingResponseDto getOneCoaching(Long longId, Long id) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    log.info(String.valueOf(coachings.size()));
//    Coaching foundCoaching;
    for(Coaching coaching : coachings) {
      if(coaching.getId() == id) {
        return CoachingMapper.instance.coachingToGetOneCoachingResponseDto(coaching);
      }
    }
    throw new EntityNotFoundException();
  }
  /**
   * 해당 코치의 모든 코칭 조회
   */
  @Transactional(readOnly = true)
  public List<CoachesCoachingsResponseDto> getCoachesCoachings(Long longId) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    List<CoachesCoachingsResponseDto> list = CoachingMapper.instance.coachingToCoachesCoachingsResponseDto(coachings);

    return list;
  }
  /**
   * 해당 코치의 코칭 정보 수정
   */
  public void changeCoachingInfo(Long longId, Long id, CoachingInfoChangeRequestDto dto) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    for(Coaching coaching : coachings) {
      if(coaching.getId() == id) {
        Category main = categoryRepository.findByName(dto.getMain());
        Category sub = categoryRepository.findByName(dto.getSub());
        coaching.updatedCoaching(main, sub, dto.getName(), dto.getSummary(), dto.getHtmlDocs());
      }
    }
  }
  /**
   * 해당 코치의 코칭 삭제
   */
  public void deleteCoaching(Long longId, Long id) {
    Coaching coaching = coachingRepository.getReferenceById(id);
    coachingRepository.delete(coaching);
  }
}
