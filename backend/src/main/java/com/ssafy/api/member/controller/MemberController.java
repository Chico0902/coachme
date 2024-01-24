package com.ssafy.api.member.controller;

import com.ssafy.api.member.dto.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.MemberInfoResponseDto;
import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.api.member.dto.UpdateMemberDto;
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
//  private final FindPwService findPwService;

  // [member-14] 회원 ID를 중복검사한다.
  @PostMapping("/duplicate/id")
  public ResponseEntity<Map<String, String>> checkMemberId(@RequestBody MemberDuplicateRequestDto memberDuplicateRequestDto) {
    String memberId = memberDuplicateRequestDto.getId();
    memberService.checkMemberId(memberId);

    try {
//      Map<String, String> responseMap = new HashMap<>();
//      responseMap.put("message", "Member registered successfully");
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  // [member-2] 회원가입 요청 시 해당 정보를 DB에 저장한다.
  @PostMapping
  public ResponseEntity<Map<String, String>> regist(@RequestBody RegistMemberDto registMemberDto) {
    try {
      log.info("memberRegistDto : {}", registMemberDto);

      memberService.regist(registMemberDto);

      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("message", "Member registered successfully");
      return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      Map<String, String> errorMap = new HashMap<>();
      errorMap.put("error", "Error registering member");
      return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // [member-5] 기본 회원정보 조회
  @GetMapping("/{id}")
  public ResponseEntity<MemberInfoResponseDto> getMemberByUsername(@PathVariable(name = "id") Long id) {
//  public void getMemberByUsername(@PathVariable(name = "id") Long id) {
//    RegistMemberDto member = memberService.findByMemberId(id);
    MemberInfoResponseDto dto = memberService.findById(id);

    log.debug("member : {} ", dto);
    if (dto != null) {
//      log.info("memberId : {}", member.getId());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // [member-13] 회원을 탈퇴한다
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteMember(@PathVariable("id") Long id) {
    try {
      memberService.deleteMember(id);
      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("message", "Member deleted successfully");
      return new ResponseEntity<>(responseMap, HttpStatus.OK);
    } catch (Exception e) {
      // 예외 발생 시 로그 출력
      e.printStackTrace();
      Map<String, String> errorMap = new HashMap<>();
      errorMap.put("error", "Error delete member");
      return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // [member-6] 회원정보 수정
  @PatchMapping("/{id}")
  public ResponseEntity<Map<String, String>> UpdateMember(@PathVariable("id") Long id, @RequestBody UpdateMemberDto updateMemberDto) {
    try {
      memberService.updateMember(id, updateMemberDto);
      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("message", "Member information updated successfully");
      return new ResponseEntity<>(responseMap, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      Map<String, String> errorMap = new HashMap<>();
      errorMap.put("error", "Error updating member information");
      return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
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

  //Email과 name의 일치여부를 check하는 컨트롤러
//  @GetMapping("/check/findPw")
//  public @ResponseBody Map<String, Boolean> pw_find(String userEmail, String userName){
//    Map<String,Boolean> json = new HashMap<>();
//    boolean pwFindCheck = userService.userEmailCheck(userEmail,userName);
//
//    System.out.println(pwFindCheck);
//    json.put("check", pwFindCheck);
//    return json;
//  }

  //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
  @PostMapping("/passwords")
  public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String memberId, @RequestParam String email) {
    try {
//      findPwService.requestResetPassword(memberId, email);
      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("message", "Reset password email sent successfully");
      return new ResponseEntity<>(responseMap, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();

      // 에러 응답
      Map<String, String> errorMap = new HashMap<>();
      errorMap.put("error", "Error sending reset password email");
      return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
//  @PutMapping("/reset")
//  public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
//    try {
//      resetPasswordService.resetPassword(username, newPassword);
//      return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return new ResponseEntity<>("Error resetting password", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//  public @ResponseBody void sendEmail(String userEmail, String userName){
//    MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
//    sendEmailService.mailSend(dto);
//
//  }

//  id : String(max : 20)
//  email : String(max : 50)

  //회원정보 수정
//  @PatchMapping("/{id}")
//  public ResponseEntity<String> UpdateMember(@PathVariable("id") Long memberId, @RequestBody UpdateMemberDto updateMemberDto) {
//    try {
//      memberService.updateMember(memberId, updateMemberDto);
//      return new ResponseEntity<>("Member information updated successfully", HttpStatus.OK);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return new ResponseEntity<>("Error updating member information", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
}
