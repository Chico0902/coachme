import { expect, it, describe } from 'vitest'
import { validateChangePassword } from '../member'

describe('비밀번호 찾기 요청 유효성 검증 테스트', () => {
  it('정상 비밀번호 찾기 요청', () => {
    // given
    const id = 'ssafy'
    const email = 'ssafy@ssafy.com'
    // when
    expect(validateChangePassword(id, email))
      // then
      .toBeTruthy()
  })

  it('아이디는 4글자 이상이어야 한다.', () => {
    // given
    const id = 'ssa'
    const email = 'ssafy@ssafy.com'
    // when then
    expect(() => validateChangePassword(id, email)).toThrowError('잘못된 비밀번호 변경 요청입니다.')
  })

  it('아이디는 영어, 숫자로만 이루어져야 한다.', () => {
    // given
    const id = '남싸피'
    const email = 'ssafy@ssafy.com'
    // when then
    expect(() => validateChangePassword(id, email)).toThrowError('잘못된 비밀번호 변경 요청입니다.')
  })

  it('아이디는 공백이 있으면 안된다.', () => {
    // given
    const id = 'ssa fy'
    const email = 'ssafy@ssafy.com'
    // when then
    expect(() => validateChangePassword(id, email)).toThrowError('잘못된 비밀번호 변경 요청입니다.')
  })

  it('아이디는 비어있으면 안된다.', () => {
    //given
    const id = ''
    const email = 'ssafy@ssafy.com'
    //when then
    expect(() => validateChangePassword(id, email)).toThrowError('잘못된 비밀번호 변경 요청입니다.')
  })

  it('이메일은 비어있으면 안된다.', () => {
    //given
    const id = 'ssafy'
    const email = ''
    //when then
    expect(() => validateChangePassword(id, email)).toThrowError('잘못된 비밀번호 변경 요청입니다.')
  })
})
