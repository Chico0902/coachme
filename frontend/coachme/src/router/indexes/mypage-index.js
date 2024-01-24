import { checkPrivilege } from '../middlewares/check-privilege'
export default [
  {
    path: '/mypage',
    beforeEnter: checkPrivilege,
    component: () => import('@/views/mypages/MypageMainView.vue'),
    children: [
      { path: '', name: 'mypage' },
      {
        path: 'coach',
        children: [
          {
            path: 'regist',
            name: 'Desktop-5-1',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/manage/CoachRegistView.vue')
            }
          },
          {
            path: 'manage',
            children: [
              {
                path: 'portfolio',
                name: 'Desktop-5-2',
                props: true,
                components: {
                  sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
                  topbar: () => import('@/components/molecules/MypageTopBar.vue'),
                  main: () => import('@/views/mypages/manage/PortfolioView.vue')
                }
              },
              {
                path: 'coaching',
                name: 'Desktop-5-3',
                components: {
                  sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
                  topbar: () => import('@/components/molecules/MypageTopBar.vue'),
                  main: () => import('@/views/mypages/manage/CoachingView.vue')
                }
              },
              {
                path: 'coaching-create',
                name: 'Desktop-5-4',
                components: {
                  sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
                  topbar: () => import('@/components/molecules/MypageTopBar.vue'),
                  main: () => import('@/views/mypages/manage/CoachingCreateView.vue')
                }
              },
              {
                path: 'video',
                name: 'Desktop-5-5',
                components: {
                  sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
                  topbar: () => import('@/components/molecules/MypageTopBar.vue'),
                  main: () => import('@/views/mypages/manage/VideoView.vue')
                }
              },
              {
                path: 'video-edit',
                name: 'Desktop-5-6',
                components: {
                  sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
                  topbar: () => import('@/components/molecules/MypageTopBar.vue'),
                  main: () => import('@/views/mypages/manage/EditVideoView.vue')
                }
              }
            ]
          }
        ]
      },
      {
        path: 'profile',
        name: 'Desktop-6',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/ProfileView.vue')
        }
      },
      {
        path: 'coaching',
        children: [
          {
            path: 'coame',
            name: 'Desktop-7-1',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/coaching/CoameScheduleView.vue')
            }
          },
          {
            path: 'coach',
            name: 'Desktop-7-2',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/coaching/CoachScheduleView.vue')
            }
          },
          {
            path: 'live-create',
            name: 'Desktop-7-3',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/coaching/CreateLiveCoachingView.vue')
            }
          }
        ]
      },
      {
        path: 'interest',
        name: 'Desktop-8',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/InterestView.vue')
        }
      },
      {
        path: 'video',
        name: 'Desktop-9',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/WatchingVideoView.vue')
        }
      },
      {
        path: 'resign',
        name: 'Desktop-10',
        props: true,
        components: {
          sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
          main: () => import('@/views/mypages/ResignView.vue')
        }
      },
      {
        path: 'admin',
        children: [
          {
            path: 'list',
            name: 'Desktop-11',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/admin/MemberListView.vue')
            }
          },
          {
            path: 'elevations',
            name: 'Desktop-12',
            props: true,
            components: {
              sidebar: () => import('@/components/molecules/MypageSidebar.vue'),
              main: () => import('@/views/mypages/admin/ElevationListView.vue')
            }
          }
        ]
      }
    ]
  }
]
