import { createRouter, createWebHistory } from 'vue-router'
import ArticleList from '../views/ArticleList.vue'
import CategoryList from '../views/CategoryList.vue'
import TagList from '../views/TagList.vue'
import UserList from '../views/UserList.vue'

const routes = [
  { path: '/', redirect: '/articles' },
  { path: '/articles', component: ArticleList },
  { path: '/categories', component: CategoryList },
  { path: '/tags', component: TagList },
  { path: '/users', component: UserList }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router