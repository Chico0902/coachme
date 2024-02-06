export default [
  {
    path: '/live',
    children: [
      { path: 'coach', name: 'Desktop-17-1', component: () => import('../../views/live/LiveCoach.vue') },
      { path: 'coame', name: 'Desktop-17-2', component: () => import('../../views/live/LiveCoame.vue') }
    ]
  }
]
