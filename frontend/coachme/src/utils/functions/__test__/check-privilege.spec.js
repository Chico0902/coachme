import { expect, it, describe } from 'vitest'
import { checkPrivilegeOverDemand } from '../auth'

describe('권한 체크 함수 테스트', () => {
  it('올바른 권한 요청', () => {
    // given
    const token =
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs'
    // when
    const demand = 1
    // then
    expect(checkPrivilegeOverDemand(token, demand)).toBeTruthy()
  })

  it('권한 형식이 잘못되었을 경우 : MASTER', () => {
    // given
    const token =
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMjMiLCJuYW1lIjoi6rOg7JaR7J20IiwicHJpdmlsZWdlIjoiTUFTVEVSIn0.JjtbPLsojLKBCxjC23ZU-fbf5zPAGv9TKB0W8DDIPqw'
    // when
    expect(() => checkPrivilegeOverDemand(token, 1))
      // then
      .toThrowError('잘못된 권한 형식입니다.')
  })

  it(`권한이 부족할 경우`, () => {
    // given
    const token1 = // 권한 1
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs'
    const token2 = // 권한 2
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMjMiLCJuYW1lIjoi6rOg7JaR7J20IiwicHJpdmlsZWdlIjoiQ09BQ0gifQ.J_2_yAO-cD8Wq-ZhUFyjDw8d1bPZATOAVgXmlWFxKIU'
    // when
    const demand1 = 2
    const demand2 = 3
    // then
    expect(() => checkPrivilegeOverDemand(token1, demand1)).toThrowError(
      '권한이 유효하지 않습니다.'
    )
    expect(() => checkPrivilegeOverDemand(token2, demand2)).toThrowError(
      '권한이 유효하지 않습니다.'
    )
  })
})
