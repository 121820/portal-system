<template>
  <div class="article-detail">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>{{ article.title }}</span>
          <el-button class="button" @click="handleBack">返回列表</el-button>
        </div>
      </template>
      
      <div class="article-meta">
        <el-tag type="info">{{ article.categoryName }}</el-tag>
        <el-tag v-for="tag in article.tags" :key="tag.id" style="margin-left: 8px;">{{ tag.name }}</el-tag>
        <div class="meta-info">
          <span>作者: {{ article.authorName }}</span>
          <span>发布时间: {{ formatDate(article.createdAt) }}</span>
          <span>浏览量: {{ article.viewCount }}</span>
          <span>状态: {{ article.statusText }}</span>
        </div>
      </div>
      
      <div class="article-content" v-html="article.content"></div>
      
      <div class="article-actions" style="margin-top: 20px;">
        <el-button @click="handleEdit">编辑</el-button>
        <el-button type="danger" @click="handleDelete">删除</el-button>
        <el-button @click="handlePreview">预览</el-button>
      </div>
    </el-card>
    
    <!-- 预览对话框 -->
    <el-dialog v-model="previewVisible" title="文章预览" width="80%" destroy-on-close>
      <div class="preview-content" v-html="article.content"></div>
    </el-dialog>
  </div>
</template>

<script>
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

export default {
  data() {
    return {
      article: {
        id: null,
        title: '',
        categoryName: '',
        authorName: '',
        viewCount: 0,
        statusText: '',
        createdAt: '',
        tags: [],
        content: ''
      },
      previewVisible: false
    }
  },
  async mounted() {
    await this.loadArticle()
  },
  methods: {
    async loadArticle() {
      try {
        const route = useRoute()
        const articleId = route.params.id
        
        const response = await axios.get(`/api/v1/articles/${articleId}`)
        const articleData = response.data.data
        
        this.article = {
          ...articleData,
          categoryName: articleData.category?.name || '未分类',
          authorName: articleData.author?.nickname || '未知',
          statusText: articleData.status === 1 ? '已发布' : '草稿',
          tags: articleData.tags || []
        }
      } catch (error) {
        console.error('加载文章详情失败:', error)
        ElMessage.error('加载文章详情失败')
        this.handleBack()
      }
    },
    handleBack() {
      const router = useRouter()
      router.push('/articles')
    },
    handleEdit() {
      const router = useRouter()
      router.push(`/articles/edit/${this.article.id}`)
    },
    handleDelete() {
      ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/v1/articles/${this.article.id}`)
          ElMessage.success('删除成功')
          this.handleBack()
        } catch (error) {
          console.error('删除文章失败:', error)
          ElMessage.error('删除文章失败')
        }
      }).catch(() => {})
    },
    handlePreview() {
      this.previewVisible = true
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.article-meta {
  margin-bottom: 20px;
}
.meta-info {
  margin-top: 10px;
  font-size: 14px;
  color: #666;
}
.meta-info span {
  margin-right: 20px;
}
.article-content {
  line-height: 1.6;
  font-size: 16px;
}
.preview-content {
  line-height: 1.6;
  font-size: 16px;
}
</style>