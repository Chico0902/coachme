import { expect, it, describe } from 'vitest'
import {
  validateId,
  validatePassword,
  validateName,
  validateNickName,
  validateEmail,
  validateRegist
} from '../member'

describe('회원가입 기능 테스트', () => {
  it('정상 회원가입', () => {
    // given
    const id = 'ssafy'
    const pw = 'ssafy1234!'
    const name = '김싸피'
    const nick = 'SSaFy'
    const email = 'ssafy@ssafy.com'
    // when
    expect(validateRegist(id, pw, name, nick, email))
      // then
      .toBeTruthy()
  })

  it('아이디는 한글, 영어, 숫자로만 이루어져야 한다.', () => {
    // given
    const id = '!!'
    // when
    expect(validateId(id))
      // then
      .toThrowError('invalid input')
  })

  it('아이디는 공백이 있으면 안된다.', () => {
    // given
    const id = 'ssa fy'
    // when
    expect(validateId(id))
      // then
      .toThrowError('invalid input')
  })

  it('아이디는 비어있으면 안된다.', () => {
    //given
    const id = ''
    //when
    expect(validateId(id))
      //then
      .toThrowError('not empty')
  })

  it('비밀번호는 9글자 이상이어야 한다.', () => {
    // given
    const pw = 'Ssafy12!'
    // when
    expect(validatePassword(pw))
      // then
      .toThrowError('password not enough length')
  })

  it('비밀번호는 대문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'ssafy1234!'
    // when
    expect(validatePassword(pw))
      // then
      .toThrowError('not include upper characters')
  })

  it('비밀번호는 특수문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'Ssafy12345'
    // when
    expect(validatePassword(pw))
      // then
      .toThrowError('not include special characters')
  })

  it('이름은 한글만 가능하다.', () => {
    // given
    const name = 'namssafy'
    // when
    expect(validateName(name))
      // then
      .toThrowError('invalid input')
  })

  it('이름은 공백이 있으면 안된다.', () => {
    //given
    const name = '남 싸피'
    // when
    expect(validateName(name))
      // then
      .toThrowError('invalid input')
  })

  it('이름은 비어있으면 안된다.', () => {
    //given
    const name = ''
    // when
    expect(validateName(name))
      // then
      .toThrowError('not empty')
  })

  it('닉네임은 한글, 영어, 숫자만 가능하다.', () => {
    //given
    const nick = '!'
    //when
    expect(validateNickName(nick))
      //then
      .toThrowError('invalid input')
  })

  it('닉네임은 비어있으면 안된다.', () => {
    //given
    const nick = ''
    //when
    expect(validateNickName(nick))
      //then
      .toThrowError('not empty')
  })

  it('이메일은 비어있으면 안된다.', () => {
    //given
    const email = ''
    //when
    expect(validateEmail(email))
      //then
      .toThrowError('not empty')
  })
})
