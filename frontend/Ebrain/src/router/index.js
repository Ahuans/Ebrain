import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {
    path: '/',
    name: 'login',
    component: ()=>import("../App.vue")
    },
    {
      path: '/admin',
      name: 'zooKeeperView',
      component: ()=>import("../views/ZooKeeperView.vue")
    },
    {
      path: '/ApiTester',
      name: 'apiTester',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/ApiTester.vue')
    },
    {
      path: '/log',
      name: 'log',
      component: ()=>import("../views/log.vue")
    }
  ]
})

export default router
