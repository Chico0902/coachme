import { HttpResponse, http } from 'msw'
const mockAdminMembersResponseDtos = {
  data: [
    {
      id: 'ssafy',
      priv: 'COAME',
      name: '김싸피',
      nick: 'kssafy',
      email: 'ssafy@ssafy.com',
      cdate: '2024-01-22',
      elev: true
    },
    {
      id: 'coach',
      priv: 'COACH',
      name: '김코치',
      nick: 'kcoach',
      email: 'ssafy12@ssafy.com',
      cdate: '2024-01-22',
      elev: false
    },
    {
      id: 'admin',
      priv: 'ADMIN',
      name: '관리자',
      nick: 'admin',
      email: 'admin@ssafy.com',
      cdate: '2024-01-22',
      elev: false
    }
  ]
}

export default [
  http.get(`http://localhost/admin/members`, () => {
    return HttpResponse.json(mockAdminMembersResponseDtos)
  })
]
