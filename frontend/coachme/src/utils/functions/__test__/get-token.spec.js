import { expect, it, describe, afterEach } from 'vitest'
import { getAccessToken } from '../auth'

describe('토큰 조회 테스트', () => {
  it('정상 토큰 확인', () => {
    // given
    const token = {
      accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs`,
      refreshToken: ''
    }
    const accessToken = `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs`
    // when
    sessionStorage.setItem('auth', JSON.stringify(token))
    // then
    expect(getAccessToken()).toBe(accessToken)
  })

  it('세션스토리지에 토큰이 없을 때', () => {
    // given
    // when
    // then
    expect(() => getAccessToken()).toThrowError('로그인 정보가 없습니다.')
  })

  it('토큰에 accessToken이 없을 때', () => {
    // given
    const token = {
      refreshToken: ''
    }
    // when
    sessionStorage.setItem('auth', JSON.stringify(token))
    // then
    expect(() => getAccessToken()).toThrowError('잘못된 토큰 형식입니다.')
  })

  // 세션스토리지 초기화
  afterEach(() => {
    sessionStorage.setItem('auth', '')
  })
})
