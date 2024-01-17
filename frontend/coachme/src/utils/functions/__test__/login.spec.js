import { expect, it, describe } from 'vitest'
import { validateLogin, validateId, validatePassword } from '../member'

describe('로그인 기능 테스트', () => {
  it('정상 로그인 요청', () => {
    // given
    const id = 'ssafy'
    const pw = 'Ssafy1234!'
    // when
    expect(validateLogin(id, pw))
      // then
      .toBeTruthy()
  })
  it('입력이 없으면 안된다.', () => {
    // given
    const id = ''
    const pw = ''
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('기입하지 않은 항목이 있습니다.')
  })

  it('아이디만 입력하면 안된다.', () => {
    // given
    const id = 'ssafy'
    const pw = ''
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('비밀번호의 길이가 충분하지 않습니다.')
  })

  it('비밀번호만 입력하면 안된다.', () => {
    // given
    const id = ''
    const pw = 'ssafy1234!'
    // when
    expect(() => validateLogin(id, pw))
      // then
      .toThrowError('기입하지 않은 항목이 있습니다.')
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
    // when
    ids.forEach((id) => {
      expect(() => validateId(id))
        // then
        .toThrowError('invalid')
    })
  })
  it('한글이 포함되면 안된다.', () => {
    //given
    const id = '남상엽'
    //when
    expect(() => validateId(id))
      //then
      .toThrowError('invalid')
  })
  it('비밀번호는 9글자 이상이어야 한다.', () => {
    // given
    const pw = 'Ssafy12!'
    // when
    expect(() => validatePassword(pw))
      // then
      .toThrowError('not enough length')
  })

  it('비밀번호는 대문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'ssafy1234!'
    // when
    expect(() => validatePassword(pw))
      // then
      .toThrowError('not include')
  })

  it('비밀번호는 특수문자를 1개 이상 포함해야 한다.', () => {
    // given
    const pw = 'Ssafy12345'
    // when
    expect(() => validatePassword(pw))
      // then
      .toThrowError('not include')
  })
})
