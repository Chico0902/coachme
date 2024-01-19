package com.ssafy.api.member.controller;

import com.ssafy.api.member.dto.UploadProfileRequestDto;
import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.api.member.service.MemberService;
import com.ssafy.api.member.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

  @PostMapping
  public ResponseEntity<Map<String, Object>> registMember(@RequestBody @Validated RegistMemberDto registMemberDto) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      memberService.regist(registMemberDto);
      responseMessage.put("message", "Regist Done !");
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findMember(@PathVariable String id) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      RegistMemberDto registMemberDto = memberService.find(id);
      responseMessage.put("data", registMemberDto);
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

  // 프로필 사진 등록 api

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
