package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CalenderResponseDto;
import com.ssafy.api.coach.dto.response.CoachesResponseDtos;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.LiveCoaching;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.type.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachService {
  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final CategoryRepository categoryRepository;

  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.getReferenceById(id).getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.getReferenceById(id).updatePortfolio(dto.getHtmlDocs());
  }

  //  String division1;
  //  String division2;
  public List<?> getCoachList(CoachesRequestDto dto) {
    List<CoachesResponseDtos> list;
    Long mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, dto.getDivision1());

    if (dto.getDivision2().isBlank()) {
      list = coachingRepository.findByCategory(mainCategoryId, null);
    } else {
      Long subCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.SUB, dto.getDivision2());

      list = coachingRepository.findByCategory(mainCategoryId, subCategoryId);

    }
    return list;
  }

  /**
   * 코치가 생성한 모든 라이브 코칭 정보를 리스트로 반환하는 메서드
   * @param longId - 코치 ID
   * @return - 라이브 코칭 정보 리스트
   */
  public List<CalenderResponseDto> getCalender(Long longId) {

    List<Coaching> coachingList = coachingRepository.findByCoachId(longId);
    List<CalenderResponseDto> list = new ArrayList<>();

    for(Coaching c : coachingList) {
      for(LiveCoaching lc : c.getLiveCoachings()) {

        CalenderResponseDto calenderResponseDto = new CalenderResponseDto();
        calenderResponseDto.setId(lc.getId());
        calenderResponseDto.setClassName(c.getName());

        String[] dateAndTime = getDateAndTime(lc.getCoachingDate());
        calenderResponseDto.setDate(dateAndTime[0]);
        calenderResponseDto.setTime(dateAndTime[1]);
        list.add(calenderResponseDto);
      }
    }

    return list;
  }

  /**
   * 시간을 날짜와 시간으로 포맷팅하는 메서드
   * @param localDateTime - 라이브 코칭 시간
   * @return - 날짜와 시간을 담은 String 배열
   */
  private String[] getDateAndTime(LocalDateTime localDateTime) {

    String[] result = new String[2];
    DateTimeFormatter format_date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");

    result[0] = localDateTime.format(format_date);
    result[1] = localDateTime.format(format_time);

    return result;
  }
}
