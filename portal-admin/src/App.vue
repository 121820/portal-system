<template>
  <div id="app">
    <el-container style="height: 100vh;">
      <el-aside width="200px" style="background-color: #f5f7fa;">
        <Sidebar />
      </el-aside>
      <el-container>
        <el-header style="background-color: #fff; box-shadow: 0 1px 4px rgba(0,21,41,.08);">
          <div class="header-title">Portal 管理后台</div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'

export default {
  components: {
    Sidebar
  }
}
</script>

<style>
.header-title {
  font-size: 18px;
  font-weight: bold;
  line-height: 60px;
}
</style>