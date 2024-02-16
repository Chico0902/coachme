export default [
  {
    path: '/mypage-coame',
    component: () => import('@/views/mypages/MypageMainView.vue'),
    children: [
      { path: '', name: 'mypage-coame' },
      {
        path: 'regist',
        name: 'Desktop-5-1',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coame/CoachRegistView.vue')
        }
      },
      {
        path: 'profile',
        name: 'Desktop-6',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coame/ProfileChangeView.vue')
        }
      },
      {
        path: 'coaching',
        name: 'Desktop-7',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coame/CoachingScheduleView.vue')
        }
      },
      {
        path: 'resign',
        name: 'Desktop-10',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coame/ResignView.vue')
        }
      }
    ]
  }
]
