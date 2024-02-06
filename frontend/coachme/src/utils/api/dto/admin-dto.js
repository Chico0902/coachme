export class AdminElevateRequestDto {
  /**
   * API번호 : member-1
   * 권한상승 요청을 위한 DTO 클래스
   * @param {*} ids
   */
  constructor(array) {
    this.ids = []
    this.makeArrayToDto(array)
  }
  /**
   * 배열 목록을 받아 dto에 id를 저장하는 함수
   * @param {*} selected 권한상승 요청을 위해 선택한 계정 목록
   */
  makeArrayToDto(array) {
    array.forEach((member) => {
      this.ids.push(member.longId)
    })
  }
}
