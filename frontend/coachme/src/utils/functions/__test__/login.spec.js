import { expect, it, describe } from 'vitest'
import { validateLogin } from '../login'

describe('로그인 기능 테스트', () => {
  it('정상 로그인 요청', () => {
    // given
    const id = 'ssafy'
    const pw = 'ssafy1234!'
    // when
    expect(validateLogin(id, pw))
      // then
      .toBeTruthy()
  })
  it('입력이 없을 때 안된다.', () => {
    // given
    const id = ''
    const pw = ''
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('not empty')
  })

  it('아이디만 입력했을때 안된다.', () => {
    // given
    const id = 'ssafy'
    const pw = ''
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('not empty')
  })

  it('비밀번호만 입력했을때 안된다.', () => {
    // given
    const id = ''
    const pw = 'ssafy1234!'
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('not empty')
  })

  it('아이디에 특수문자가 들어가면 안된다.', () => {
    // given
    const ids = [
      '!',
      '@',
      '#',
      '$',
      '%',
      '^',
      '&',
      '*',
      '(',
      ')',
      '{',
      '}',
      '-',
      '_',
      '+',
      '=',
      '`',
      '~',
      '\\',
      '\n',
      ':',
      ';',
      '[',
      ']',
      '|'
    ]
    const pw = 'ssafy1234!'
    // when
    ids.forEach((id) => {
      expect(() => validateLogin(id, pw))
        // then
        .toThrowError('invalid')
    })
  })
  it('한글이 포함되면 안된다.', () => {
    //given
    const id = '남상엽'
    const pw = 'ssafy1234!'
    //when
    expect(() => validateLogin(id, pw))
      //then
      .toThrowError('invalid')
  })
})
