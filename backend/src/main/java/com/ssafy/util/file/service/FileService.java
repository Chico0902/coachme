package com.ssafy.util.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.File;
import com.ssafy.db.entity.Member;
import com.ssafy.util.file.Mapper.FileMapper;
import com.ssafy.util.file.dto.response.FileResponseDto;
import com.ssafy.util.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileService {

  private final FileRepository fileRepository;
  private final MemberRepository memberRepository;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final AmazonS3 amazonS3;


  // 파일 업로드
  @Transactional
  public String uploadFileList(List<MultipartFile> multipartFiles) {
    // 파일 업로드시, 파일명을 난수화하기 위해 UUID 를 활용하여 난수 생성
    String fileKey = UUID.randomUUID().toString();

    for (int i = 0; i < multipartFiles.size(); i++) {
      // AWS S3에 업로드할 객체의 메타데이터를 설정 -> 파일을 로컬에 따로 저장하지 않고 바로 S3에 저장하기 위함
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(multipartFiles.get(i).getSize());
      objectMetadata.setContentType(multipartFiles.get(i).getContentType());

      // AWS S3에 파일 업로드 하는 부분. 파일 업로드 필요시 주석 제거 후 사용
      try (InputStream inputStream = multipartFiles.get(i).getInputStream()) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileKey, inputStream, objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
      } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
      }
    }

    return amazonS3.getUrl(bucket, fileKey).toString();
  }


  // 파일 조회
  public List<FileResponseDto> getFile(List<Long> fileIdList) {
    List<File> fileList = fileRepository.findAllById(fileIdList);

    return FileMapper.instance.fileToFileResponseDtoList(fileList);
  }


  // 파일 삭제
  @Transactional
  public void deleteFile(Long fileId) {
    File file = fileRepository.getReferenceById(fileId);

    // MYSQL에서 객체 삭제
    fileRepository.delete(file);

    // S3에서 객체 삭제
    amazonS3.deleteObject(new DeleteObjectRequest(bucket, extractFileNameFromUri(file.getUrl())));
  }

  // 파일 uri에서 이름 추출
  public String extractFileNameFromUri(String uriString) {
    String fileName = null;
    try {
      URI uri = new URI(uriString);
      String path = uri.getPath();

      // 파일 이름 추출
      String[] pathSegments = path.split("/");
      fileName = pathSegments[pathSegments.length - 1];
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    return fileName;
  }
}
