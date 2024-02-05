import { expect, it, describe } from 'vitest'
import {
  validateId,
  validatePassword,
  validatePasswordConfirm,
  validateName,
  validateNickName,
  validateEmail,
  validateRegist
} from '../member'

describe('회원가입 기능 테스트', () => {
  it('정상 회원가입', () => {
    // given
    const id = 'ssafy'
    const pw = 'Ssafy1234!'
    const pwConfirm = 'Ssafy1234!'
    const name = '김싸피'
    const nick = 'SSaFy'
    const email = 'ssafy@ssafy.com'
    // when then
    expect(validateRegist(id, pw, pwConfirm, name, nick, email)).toBeTruthy()
  })

  it('정상 아이디 : ssafy12', () => {
    // given
    const id = 'ssafy12'
    // when then
    expect(validateId(id)).toBeTruthy()
  })

  it('아이디는 4글자 이상이어야 합니다.', () => {
    // given
    const id = 'ssa'
    // when then
    expect(validateId(id)).toBeFalsy()
  })

  it('아이디는 영어, 숫자로만 이루어져야 한다.', () => {
    // given
    const id = '남싸피'
    // when then
    expect(validateId(id)).toBeFalsy()
  })

  it('아이디는 공백이 있으면 안된다.', () => {
    // given
    const id = 'ssa fy'
    // when then
    expect(validateId(id)).toBeFalsy()
  })

  it('아이디는 비어있으면 안된다.', () => {
    //given
    const id = ''
    //when then
    expect(validateId(id)).toBeFalsy()
  })

  it('정상 비밀번호 : Ssafy1234!', () => {
    // given
    const pw = 'Ssafy1234!'
    // when then
    expect(validatePassword(pw)).toBeTruthy()
  })

  it('비밀번호는 9글자 이상이어야 한다.', () => {
    // given
    const pw = 'Ssafy12!'
    // when then
    expect(validatePassword(pw)).toBeFalsy()
  })

  it('비밀번호는 대문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'ssafy1234!'
    // when then
    expect(validatePassword(pw)).toBeFalsy()
  })

  it('비밀번호는 특수문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'Ssafy12345'
    // when then
    expect(validatePassword(pw)).toBeFalsy()
  })

  it('비밀번호는 비밀번호 확인과 같아야 한다.', () => {
    // given
    const pw = 'Ssafy12345'
    const pwConfirm = 'Ssafy12344'
    // when then
    expect(validatePasswordConfirm(pw, pwConfirm)).toBeFalsy()
  })

  it('정상 이름 : 남싸피', () => {
    // given
    const name = '남싸피'
    // when then
    expect(validateName(name)).toBeTruthy()
  })

  it('이름은 한글만 가능하다.', () => {
    // given
    const name = 'namssafy'
    // when then
    expect(validateName(name)).toBeFalsy()
  })

  it('이름은 자모음만 입력할 수 없다.', () => {
    // given
    const name = 'ㄴ'
    // when then
    expect(validateName(name)).toBeFalsy()
  })

  it('이름은 공백이 있으면 안된다.', () => {
    //given
    const name = '남 싸피'
    // when then
    expect(validateName(name)).toBeFalsy()
  })

  it('이름은 비어있으면 안된다.', () => {
    //given
    const name = ''
    // when then
    expect(validateName(name)).toBeFalsy()
  })

  it('정상 닉네임 : SsaFy', () => {
    //given
    const nick = 'SsaFy'
    //when then
    expect(validateNickName(nick)).toBeTruthy()
  })

  it('닉네임은 한글, 영어, 숫자만 가능하다.', () => {
    //given
    const nick = '!'
    //when then
    expect(validateNickName(nick)).toBeFalsy()
  })

  it('닉네임은 비어있으면 안된다.', () => {
    //given
    const nick = ''
    //when then
    expect(validateNickName(nick)).toBeFalsy()
  })

  it('이메일은 비어있으면 안된다.', () => {
    //given
    const email = ''
    //when then
    expect(validateEmail(email)).toBeFalsy()
  })
})
