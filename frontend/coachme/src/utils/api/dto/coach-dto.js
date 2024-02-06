export class categoriesDto {
  /**
   * API번호 : member-4
   * 권한상승 요청을 위한 DTO 클래스
   * @param {String} division1
   * @param {String} division2
   */
  constructor(division1, division2) {
    this.division1 = division1
    this.division2 = division2
  }
}

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

export class CreateCoachingRequestDto {
  /**
   * API번호 : coaching-2
   * @param {String} main
   * @param {String} sub
   * @param {String} name
   * @param {String} summary
   * @param {String} htmlDocs
   */
  constructor(main, sub, name, summary, htmlDocs) {
    this.main = main
    this.sub = sub
    this.name = name
    this.summary = summary
    this.htmlDocs = htmlDocs
  }
}
