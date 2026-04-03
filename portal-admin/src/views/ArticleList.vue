<template>
  <div class="article-list">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>文章管理</span>
          <el-button class="button" type="primary" @click="handleCreate">新建文章</el-button>
        </div>
      </template>
      
      <el-table :data="articles" style="width: 100%">
        <el-table-column prop="title" label="标题" width="200" />
        <el-table-column prop="categoryName" label="栏目" width="120" />
        <el-table-column prop="authorName" label="作者" width="120" />
        <el-table-column prop="viewCount" label="浏览量" width="80" />
        <el-table-column prop="statusText" label="状态" width="100" />
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
    
    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="80%">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="栏目">
          <el-select v-model="form.categoryId" placeholder="请选择栏目">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="form.tagIds" multiple placeholder="请选择标签">
            <el-option
              v-for="tag in tags"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-upload
            class="avatar-uploader"
            action="/api/v1/files/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="form.coverImage" :src="form.coverImage" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="内容">
          <el-textarea v-model="form.content" :rows="15" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'

export default {
  components: {
    Plus
  },
  data() {
    return {
      articles: [],
      categories: [],
      tags: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: null,
        title: '',
        categoryId: null,
        tagIds: [],
        coverImage: '',
        content: ''
      }
    }
  },
  computed: {
    statusText() {
      return this.status === 1 ? '已发布' : '草稿'
    }
  },
  async mounted() {
    await this.loadArticles()
    await this.loadCategories()
    await this.loadTags()
  },
  methods: {
    async loadArticles() {
      try {
        const response = await axios.get('/api/v1/articles', {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        })
        this.articles = response.data.data.map(article => ({
          ...article,
          categoryName: article.category?.name || '未分类',
          authorName: article.author?.nickname || '未知',
          statusText: article.status === 1 ? '已发布' : '草稿'
        }))
        this.total = response.data.total
      } catch (error) {
        console.error('加载文章失败:', error)
      }
    },
    async loadCategories() {
      try {
        const response = await axios.get('/api/v1/categories')
        this.categories = response.data.data
      } catch (error) {
        console.error('加载栏目失败:', error)
      }
    },
    async loadTags() {
      try {
        const response = await axios.get('/api/v1/tags')
        this.tags = response.data.data
      } catch (error) {
        console.error('加载标签失败:', error)
      }
    },
    handleCreate() {
      this.dialogTitle = '新建文章'
      this.form = {
        id: null,
        title: '',
        categoryId: null,
        tagIds: [],
        coverImage: '',
        content: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑文章'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      // TODO: 实现删除逻辑
      console.log('删除文章:', row.id)
    },
    handleAvatarSuccess(response, uploadFile) {
      this.form.coverImage = response.url
    },
    handleSave() {
      // TODO: 实现保存逻辑
      console.log('保存文章:', this.form)
      this.dialogVisible = false
      this.loadArticles()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.loadArticles()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.loadArticles()
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>