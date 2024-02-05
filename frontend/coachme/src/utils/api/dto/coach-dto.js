export class changePortfolioRequsetDto {
  /**
   * API번호 : member-4
   * 권한상승 요청을 위한 DTO 클래스
   * @param {String} longId
   * @param {String} htmlDocs
   */
  constructor(longId, htmlDocs) {
    this.longId = longId
    this.htmlDocs = htmlDocs
  }
}
