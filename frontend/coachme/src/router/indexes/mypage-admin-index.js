export default [
  {
    path: '/mypage-admin',
    component: () => import('@/views/mypages/MypageMainView.vue'),
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
