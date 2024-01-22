import { checkPrivilege } from '../middlewares/check-privilege'
export default [
  {
    path: '/mypage',
    beforeEnter: checkPrivilege,
    children: [
      { path: '', name: 'mypage' },
      {
        path: 'coach',
        children: [
          {
            path: 'regist',
            name: 'Desktop-5-1',
            component: () => import('@/views/mypages/manage/CoachRegistView.vue')
          },
          {
            path: 'manage',
            children: [
              {
                path: 'portfolio',
                name: 'Desktop-5-2',
                component: () => import('@/views/mypages/manage/PortfolioView.vue')
              },
              {
                path: 'coaching',
                name: 'Desktop-5-3',
                component: () => import('@/views/mypages/manage/CoachingView.vue')
              },
              {
                path: 'coaching-create',
                name: 'Desktop-5-4',
                component: () => import('@/views/mypages/manage/CoachingCreateView.vue')
              },
              { path: 'video', name: 'Desktop-5-5', component: () => import('@/views/mypages/manage/VideoView.vue') },
              {
                path: 'video-edit',
                name: 'Desktop-5-6',
                component: () => import('@/views/mypages/manage/EditVideoView.vue')
              }
            ]
          }
        ]
      },
      { path: 'profile', name: 'Desktop-6', component: () => import('@/views/mypages/ProfileView.vue') },
      {
        path: 'coaching',
        children: [
          {
            path: 'coame',
            name: 'Desktop-7-1',
            component: () => import('@/views/mypages/coaching/CoameScheduleView.vue')
          },
          {
            path: 'coach',
            name: 'Desktop-7-2',
            component: () => import('@/views/mypages/coaching/CoachScheduleView.vue')
          },
          {
            path: 'live-create',
            name: 'Desktop-7-3',
            component: () => import('@/views/mypages/coaching/CreateLiveCoachingView.vue')
          }
        ]
      },
      { path: 'interest', name: 'Desktop-8', component: () => import('@/views/mypages/InterestView.vue') },
      { path: 'video', name: 'Desktop-9', component: () => import('@/views/mypages/WatchingVideoView.vue') },
      { path: 'resign', name: 'Desktop-10', component: () => import('@/views/mypages/ResignView.vue') },
      {
        path: 'admin',
        children: [
          { path: 'list', name: 'Desktop-11', component: () => import('@/views/mypages/admin/MemberListView.vue') },
          {
            path: 'elevations',
            name: 'Desktop-12',
            component: () => import('@/views/mypages/admin/ElevationListView.vue')
          }
        ]
      }
    ]
  }
]
