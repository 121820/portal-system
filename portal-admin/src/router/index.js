import { createRouter, createWebHashHistory } from 'vue-router'
import ArticleList from '../views/ArticleList.vue'
import ArticleDetail from '../views/ArticleDetail.vue'
import CategoryList from '../views/CategoryList.vue'
import TagList from '../views/TagList.vue'
import UserList from '../views/UserList.vue'

const routes = [
  {
    path: '/',
    redirect: '/articles'
  },
  {
    path: '/articles',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/articles/:id',
    name: 'ArticleDetail',
    component: ArticleDetail,
    props: true
  },
  {
    path: '/categories',
    name: 'CategoryList',
    component: CategoryList
  },
  {
    path: '/tags',
    name: 'TagList',
    component: TagList
  },
  {
    path: '/users',
    name: 'UserList',
    component: UserList
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router