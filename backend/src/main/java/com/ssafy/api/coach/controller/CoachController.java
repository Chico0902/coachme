package com.ssafy.api.coach.controller;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CoachDetailResponseDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.service.CoachService;
import com.ssafy.api.coaching.dto.response.CoachingDetailResponseDto;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/categories")
  public ResponseEntity<ListDataDto> getCoachList(CoachesRequestDto dto) {
    ListDataDto listDataDto = new ListDataDto(coachService.getCoachList(dto));
    return new ResponseEntity<>(listDataDto, HttpStatus.OK);
  }

  @GetMapping("/{coachId}")
  public ResponseEntity<CoachDetailResponseDto> getCoachDetail(@PathVariable("coachId") long coachId) {
    CoachDetailResponseDto responseDto = coachService.getCoachDetail(coachId);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }


}
