package com.ssafy.api.coach.controller;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CalenderResponseDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.service.CoachService;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coaches")
@RequiredArgsConstructor
@Slf4j
public class CoachController {
  private final CoachService coachService;

  @GetMapping("/portfolio/{longId}")
  public ResponseEntity<PortfolioResponseDto> getPortfolio(@PathVariable("longId") long id) throws Exception {
    PortfolioResponseDto portfolio = coachService.getPortfolio(id);
    return new ResponseEntity<>(portfolio, HttpStatus.OK);
  }

  @PatchMapping("/portfolio/{longId}")
  public ResponseEntity<MessageDto> updatePortfolio(
      @PathVariable("longId") long id,
      @RequestBody PortfolioRequestDto portfolioRequestDto) throws Exception {
    coachService.updatePortfolio(id, portfolioRequestDto);
    return new ResponseEntity<>(new MessageDto("Portfolio update successfully completed"), HttpStatus.OK);
  }

  ///coaches/categories?division1={대분류명}&division2={소분류명}
  @GetMapping("/categories")
  public ResponseEntity<ListDataDto> getCoachList(CoachesRequestDto dto) {
    ListDataDto listDataDto = new ListDataDto(coachService.getCoachList(dto));

    return new ResponseEntity<>(listDataDto, HttpStatus.OK);
  }

  /**
   * [coach-10] 코치가 마이페이지>라이브관리 메뉴에서 자신이 만든 라이브 코칭 일정을 캘린더로 확인할 수 있다.
   * privilege : 2
   * @return - [200] list
   */
  @GetMapping("/{longId}/calender")
  public ResponseEntity<?> getCalender(@PathVariable Long longId) {

    List<CalenderResponseDto> list = coachService.getCalender(longId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }
}
