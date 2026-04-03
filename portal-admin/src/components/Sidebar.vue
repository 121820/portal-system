<template>
  <el-menu
    :default-active="activeIndex"
    class="sidebar-menu"
    mode="vertical"
    @select="handleSelect"
  >
    <el-menu-item index="/articles">
      <el-icon><Document /></el-icon>
      <span>文章管理</span>
    </el-menu-item>
    <el-menu-item index="/categories">
      <el-icon><Folder /></el-icon>
      <span>栏目管理</span>
    </el-menu-item>
    <el-menu-item index="/tags">
      <el-icon><PriceTag /></el-icon>
      <span>标签管理</span>
    </el-menu-item>
    <el-menu-item index="/users">
      <el-icon><User /></el-icon>
      <span>用户管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Document, Folder, PriceTag, User } from '@element-plus/icons-vue'

export default {
  components: {
    Document,
    Folder,
    PriceTag,
    User
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const activeIndex = ref(route.path)
    
    const handleSelect = (key) => {
      router.push(key)
    }
    
    // 监听路由变化更新激活状态
    onMounted(() => {
      activeIndex.value = route.path
    })
    
    return {
      activeIndex,
      handleSelect
    }
  }
}