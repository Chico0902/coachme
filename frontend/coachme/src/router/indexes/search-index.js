export default [
  {
    path: '/search',
    children: [
      {
        path: 'coach',
        children: [
          {
            path: 'list/:category1/:category2/:keyword',
            name: 'Desktop-13',
            component: () => import('../../views/search/coach/CoachList.vue')
          },
          {
            path: 'detail/:id',
            name: 'Desktop-15',
            component: () => import('../../views/search/coach/CoachDetail.vue')
          }
        ]
      },
      {
        path: 'coaching',
        children: [
          {
            path: 'list/:category1/:category2/:keyword',
            name: 'Desktop-14',
            component: () => import('../../views/search/coaching/CoachingList.vue')
          },
          {
            path: 'detail/:id',
            name: 'Desktop-16',
            component: () => import('../../views/search/coaching/CoachingDetail.vue'),
            props: true
          }
        ]
      }
    ]
  }
]
