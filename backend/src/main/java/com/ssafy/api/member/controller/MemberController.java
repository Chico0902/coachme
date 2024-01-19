package com.ssafy.api.member.controller;

import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.api.member.request.MemberRegistDto;
import com.ssafy.api.member.request.UpdateMemberDto;
import com.ssafy.api.member.service.FileService;
import com.ssafy.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
  private final MemberService memberService;
  private final FileService fileService;


  // 명세 1-3 회원가입 요청 시 해당 정보를 DB에 저장한다.
  @PostMapping
  public ResponseEntity<String> regist(@RequestBody RegistMemberDto memberRegistDto) {
    try {
      log.info("memberRegistDto : {}", memberRegistDto);

      memberService.regist(memberRegistDto);


      return new ResponseEntity<>("Regist Done !", HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Error registering member", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // 명세 1-6 기본 회원정보 조회
  @GetMapping("/{id}")
  public ResponseEntity<RegistMemberDto> getMemberByUsername(@PathVariable("id") Long memberId) {
    RegistMemberDto member = memberService.findByMemberId(memberId);

    if (member != null) {
//      log.info("memberId : {}", member.getId());
      return new ResponseEntity<>(member, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMember(@PathVariable("id") Long memberId) {
    try {
      memberService.deleteMember(memberId);
      return new ResponseEntity<>("Member deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      // 예외 발생 시 로그 출력
      e.printStackTrace();
      return new ResponseEntity<>("Error deleting member", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //회원정보 수정
  @PatchMapping("/{id}")
  public ResponseEntity<String> UpdateMember(@PathVariable("id") Long memberId, @RequestBody UpdateMemberDto updateMemberDto) {
    try {
      memberService.updateMember(memberId, updateMemberDto);
      return new ResponseEntity<>("Member information updated successfully", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Error updating member information", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // 프로필 사진 등록 api
  @PostMapping("/profiles/images/{id}")
  public ResponseEntity<Map<String, String>> uploadProfile(@RequestParam("file") MultipartFile file, @PathVariable("id") String memberId) {
    log.debug("Upload Profile Image : {}", file);
    log.debug("Upload MemberId : {}", memberId);

    List<MultipartFile> fileList = new ArrayList<>();
    fileList.add(file);
    fileService.uploadFileList(fileList, memberId, "profile");

    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("message", "Success!");

    return ResponseEntity.ok(responseMap);
  }


  // 프로필 사진 조회 api
  @GetMapping("/profiles/images/{id}")
  public ResponseEntity<Map<String, String>> getProfiles(@PathVariable("id") String memberId) {

    String readImageUri = fileService.getProfile(memberId);
    log.info("Read Profile Image : file uri - {}", readImageUri);

    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("profileImageUrl", readImageUri);

    return ResponseEntity.ok(responseMap);
  }

  @DeleteMapping("/profiles/images/{id}")
  public ResponseEntity<Map<String, String>> deleteFile(@PathVariable("id") String memberId) {
    log.info("Delete Profile Image : {}", memberId);
    fileService.deleteProfile(memberId);

    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("message", "Success!");

    return ResponseEntity.ok(responseMap);
  }
}
