import { expect, it, describe } from 'vitest'
import { validateProfileText, validateProfileImage } from '../member'

describe('프로필 확인 테스트', () => {
  it('정상 프로필 글 요청', () => {
    // given
    const profileText = '안녕하세용'
    // when
    expect(validateProfileText(profileText))
      // then
      .toBeTruthy()
  })
  it('프로필 글은 50자를 넘을 수 없습니다.', () => {
    // given
    const profileText = `제가 LA에 있을때는 말이죠 정말 제가 꿈에 무대인 메이저리그로 진출해서 가는 식당마다 싸인해달라 기자들은 항상 붙어다니며 취재하고 제가 그 머~ 어~ 대통령이 된 기분이였어요 그런데 17일만에 17일만에 마이너리그로 떨어졌어요 못던져서 그만두고 그냥 확 한국으로 가버리고 싶었어요 그래서 집에 가는길에...`
    // when
    expect(() => validateProfileText(profileText))
      // then
      .toThrowError('프로필 글은 50자를 넘을 수 없습니다.')
  })

  it('정상 프로필 사진 요청', () => {
    // given
    const file = {
      name: 'test.png',
      size: 30000
    }
    // when
    expect(validateProfileImage(file))
      // then
      .toBeTruthy()
  })

  it('exe파일은 업로드가 불가능하다.', () => {
    // given
    const file = {
      name: 'test.exe',
      size: 30000
    }
    // when
    expect(() => validateProfileImage(file))
      // then
      .toThrowError('잘못된 파일 형식입니다.')
  })

  it('txt파일은 업로드가 불가능하다.', () => {
    // given
    const file = {
      name: 'test.txt',
      size: 30000
    }
    // when
    expect(() => validateProfileImage(file))
      // then
      .toThrowError('잘못된 파일 형식입니다.')
  })

  it('mp4파일은 업로드가 불가능하다.', () => {
    // given
    const file = {
      name: 'test.mp4',
      size: 30000
    }
    // when
    expect(() => validateProfileImage(file))
      // then
      .toThrowError('잘못된 파일 형식입니다.')
  })

  it('용량 10MB이상 파일은 업로드가 불가능하다.', () => {
    // given
    const file = {
      name: 'test.png',
      size: 10 * 1024 * 1024 + 1
    }
    // when
    expect(() => validateProfileImage(file))
      // then
      .toThrowError('프로필 사진은 10MB를 초과할 수 없습니다.')
  })
})
