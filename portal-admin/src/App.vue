<template>
  <div id="app">
    <el-container style="height: 100vh;">
      <el-aside width="200px" style="background-color: #f5f7fa;">
        <el-menu
          :default-active="$route.path"
          router
          mode="vertical"
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
            <el-icon><Tag /></el-icon>
            <span>标签管理</span>
          </el-menu-item>
          <el-menu-item index="/users">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="text-align: right; font-size: 12px;">
          <el-dropdown>
            <el-icon style="margin-right: 8px; cursor: pointer"><Setting /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { Document, Folder, Tag, User, Setting } from '@element-plus/icons-vue'

export default {
  components: {
    Document,
    Folder,
    Tag,
    User,
    Setting
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>