export default [
  {
    path: '/mypage-coach',
    component: () => import('@/views/mypages/MypageMainView.vue'),
    children: [
      { path: '', name: 'mypage-coach' },
      {
        path: 'portfolio',
        name: 'Desktop-5-2',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/PortfolioView.vue')
        }
      },
      {
        path: 'coaching',
        name: 'Desktop-5-3',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/CoachingManageView.vue')
        }
      },
      {
        path: 'coaching-create',
        name: 'Desktop-5-4',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/CoachingCreateView.vue')
        }
      },
      {
        path: 'live',
        name: 'Desktop-5-5',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/LiveManageView.vue')
        }
      },
      {
        path: 'live-create',
        name: 'Desktop-5-6',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/LiveCreateView.vue')
        }
      },
      {
        path: 'video',
        name: 'Desktop-5-7',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/VideoManageView.vue')
        }
      },
      {
        path: 'video-edit',
        name: 'Desktop-5-8',
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/coach/VideoEditView.vue')
        }
      }
    ]
  }
]
