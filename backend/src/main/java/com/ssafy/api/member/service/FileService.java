package com.ssafy.api.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.api.auth.service.CustomUserDetailsService;
import com.ssafy.api.member.repository.FileRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.File;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileService {

  private final MemberRepository memberRepository;
  private final FileRepository fileRepository;
  private final CustomUserDetailsService customUserDetailsService;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final AmazonS3 amazonS3;


  // 파일 여러개 업로드
  @Transactional
  public void uploadFileList(List<MultipartFile> multipartFiles, String memberId, String fileType) {

    // 이전에 등록한 프로필 사진이 있는 경우 제거
    deleteProfile(memberId);

    List<String> fileNameList = new ArrayList<>();
    multipartFiles.forEach(file -> {
      // 파일 이름을 난수화하여 저장
      String fileName = createFileName(file.getOriginalFilename());

      // AWS S3에 업로드할 객체의 메타데이터를 설정 -> 파일을 로컬에 따로 저장하지 않고 바로 S3에 저장하기 위함
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(file.getSize());
      objectMetadata.setContentType(file.getContentType());

      Member member = customUserDetailsService.loadUserByUsername(memberId);
      File newFile = File.builder()
          .name(fileName)
          .url(amazonS3.getUrl(bucket, fileName).toString())
          .uploader(member)
          .build();

      log.info("profile : {}", newFile);

      try {
        fileRepository.save(newFile);
      } catch (Exception e) {
        e.printStackTrace();
      }

      // AWS S3에 파일 업로드 하는 부분. 파일 업로드 필요시 주석 제거 후 사용
//    try (InputStream inputStream = file.getInputStream()) {
//      amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//          .withCannedAcl(CannedAccessControlList.PublicRead));
//    } catch (IOException e) {
//      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
//    }
    });
  }

  // 프로필 사진 조회
  public String getProfile(String memberId) {
    File file = fileRepository.findByMemberAndType(customUserDetailsService.loadUserByUsername(memberId), "profile");
    return file.getUrl();
  }


  // 프로필 사진 삭제
  @Transactional
  public void deleteProfile(String memberId) {
    File file = fileRepository.findByMemberAndType(customUserDetailsService.loadUserByUsername(memberId), "profile");
    if (file != null) {
      try {
        // S3에서 객체 삭제
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, file.getName()));
        fileRepository.delete(file);
        log.info("Object deleted successfully. {}", file);
      } catch (Exception e) {
        log.error("Error deleting object: {}", e.getMessage());
      }
    }
  }


  // 먼저 파일 업로드시, 파일명을 난수화하기 위해 UUID 를 활용하여 난수 생성
  public String createFileName(String fileName) {
    return UUID.randomUUID().toString().concat(getFileExtension(fileName));
  }

  // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기위해, "."의 존재 유무만 판단
  private String getFileExtension(String fileName) {
    try {
      return fileName.substring(fileName.lastIndexOf("."));
    } catch (StringIndexOutOfBoundsException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
    }
  }
}
