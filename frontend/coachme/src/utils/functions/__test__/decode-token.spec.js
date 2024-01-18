import { expect, it, describe } from 'vitest'
import { decodeToken } from '../auth'

describe('토큰 디코딩 테스트', () => {
  it('정상 토큰정보 입력', () => {
    // given
    const token =
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMjMiLCJuYW1lIjoi6rOg7JaR7J20IiwicHJpdmlsZWdlIjoiQ09BTUUifQ.el7ZYY5gjG2Jr6Te2cx_Ia-qllT1-hU2Jrz69vr0PYg'
    // when
    const info = {
      uid: '123',
      name: '고양이',
      privilege: 'COAME'
    }
    // then
    expect(decodeToken(token)).toBeTruthy()
    expect(decodeToken(token)).toEqual(info)
  })

  it('토큰정보는 비어있을 수 없다', () => {
    // given
    const token = ''
    // when
    expect(() => decodeToken(token))
      // then
      .toThrowError('토큰 정보가 없습니다.')
  })

  it(`JWT는 '.'으로 구분된 세 부분(header, payload, signature)으로 이루어져 있어야 한다.`, () => {
    // given
    const token1 = 'header.payload.signature.add'
    const token2 = 'header.payload'
    // when then
    expect(() => decodeToken(token1)).toThrowError('잘못된 토큰 형식입니다.')
    expect(() => decodeToken(token2)).toThrowError('잘못된 토큰 형식입니다.')
  })
})
