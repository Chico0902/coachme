//package com.ssafy.api.member.service;
//
//import com.ssafy.api.member.Util.EncryptionUtils;
//import com.ssafy.api.member.dto.EmailDto;
//
//import com.ssafy.api.member.repository.MemberRepository;
//import com.ssafy.api.member.dto.UpdateMemberDto;
//import com.ssafy.db.entity.Member;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class FindPwService {
//
//  private final MemberRepository memberRepository;
////  private final EmailDto emailDto;
//  private final UpdateMemberDto updateMemberDto;
//  private final EncryptionUtils encryptionUtils;
//
////  public EmailDto createMailAndChangePassword(String userEmail, String userName){
////    String str = getTempPassword();
////    EmailDto dto = new EmailDto();
////    dto.setAddress(userEmail);
////    dto.setTitle(userName+"님의 HOTTHINK 임시비밀번호 안내 이메일 입니다.");
////    dto.setMessage("안녕하세요. HOTTHINK 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" +"님의 임시 비밀번호는 "
////        + str + " 입니다.");
////    updatePassword(str,userEmail);
////    return dto;
////  }
////
////  public void updatePassword(String str,String userEmail){
////    String pw = EncryptionUtils.encryptMD5(str);
////    int id = userRepository.findUserByUserId(userEmail).getId();
////    userRepository.updateUserPassword(id,pw);
////  }
//
//  public void requestResetPassword(String memberId, String email) {
////    Member member = memberRepository.findByMemberIdAndEmail(memberId, email);
////    if(member != null) {
////      String tempPw = generateTempPassword();
////      EmailDto dto = new EmailDto();
////      dto.setEmail(email);
////      dto.setTitle(memberId + "님의 CoachMe 임시비밀번호 안내 이메일 입니다.");
////      dto.setMessage("안녕하세요. CoachMe 임시비밀번호 안내 관련 이메일 입니다." + "[" + memberId + "]" +"님의 임시 비밀번호는 "
////          + tempPw + " 입니다.");
////      resetPassword(member, tempPw, email);
////    } else {
////      throw new RuntimeException("Member with the provided email not found");
////    }
//  }
//
//  public void resetPassword(Member member, String tempPw, String email) {
//    member.changeMemberInfo(updateMemberDto);
//
//
//    memberRepository.save(member);
//  }
//
//  public String generateTempPassword() {
////    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
////        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
////
////    String str = "";
////
////    int idx = 0;
////    for (int i = 0; i < 10; i++) {
////      idx = (int) (charSet.length * Math.random());
////      str += charSet[idx];
////    }
////    return str;
////  }
////}
//
////public void sendTemporaryPasswordEmail(String to, String temporaryPassword) {
////  SimpleMailMessage message = new SimpleMailMessage();
////  message.setTo(to);
////  message.setSubject("Your Temporary Password");
////  message.setText("Your temporary password is: " + temporaryPassword);
////
////  javaMailSender.send(message);
////}
//  }
//
