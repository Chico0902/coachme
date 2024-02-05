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
