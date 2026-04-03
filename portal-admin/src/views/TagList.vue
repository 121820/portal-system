<template>
  <div class="tag-list">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>标签管理</span>
          <el-button class="button" type="primary" @click="handleCreate">新建标签</el-button>
        </div>
      </template>
      
      <!-- 标签列表 -->
      <el-table :data="tags" style="width: 100%">
        <el-table-column prop="name" label="标签名称" width="200" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="articleCount" label="关联文章数" width="120" />
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
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" destroy-on-close>
      <el-form :model="form" label-width="80px" :rules="rules" ref="tagForm">
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-text="启用" inactive-text="禁用" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" rows="3" placeholder="请输入标签描述" />
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

export default {
  setup() {
    const tags = ref([])
    const total = ref(0)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const dialogVisible = ref(false)
    const dialogTitle = ref('')
    const form = ref({
      id: null,
      name: '',
      status: true,
      description: ''
    })
    const tagForm = ref(null)
    
    const rules = {
      name: [
        { required: true, message: '请输入标签名称', trigger: 'blur' },
        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
      ]
    }
    
    const loadTags = async () => {
      try {
        const response = await axios.get('/api/v1/tags', {
          params: {
            page: currentPage.value,
            size: pageSize.value
          }
        })
        tags.value = response.data.data.map(tag => ({
          ...tag,
          statusText: tag.status ? '启用' : '禁用',
          articleCount: tag.articleCount || 0
        }))
        total.value = response.data.total
      } catch (error) {
        console.error('加载标签失败:', error)
        ElMessage.error('加载标签失败')
      }
    }
    
    const handleCreate = () => {
      dialogTitle.value = '新建标签'
      form.value = {
        id: null,
        name: '',
        status: true,
        description: ''
      }
      dialogVisible.value = true
    }
    
    const handleEdit = (row) => {
      dialogTitle.value = '编辑标签'
      form.value = { ...row }
      dialogVisible.value = true
    }
    
    const handleDelete = (row) => {
      ElMessageBox.confirm(`确定要删除标签 "${row.name}" 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/v1/tags/${row.id}`)
          ElMessage.success('删除成功')
          loadTags()
        } catch (error) {
          console.error('删除标签失败:', error)
          ElMessage.error('删除标签失败')
        }
      }).catch(() => {})
    }
    
    const handleSave = async () => {
      try {
        await tagForm.value.validate()
        
        if (form.value.id) {
          // 更新标签
          await axios.put(`/api/v1/tags/${form.value.id}`, form.value)
          ElMessage.success('标签更新成功')
        } else {
          // 创建标签
          await axios.post('/api/v1/tags', form.value)
          ElMessage.success('标签创建成功')
        }
        
        dialogVisible.value = false
        loadTags()
      } catch (error) {
        console.error('保存标签失败:', error)
        ElMessage.error('保存标签失败')
      }
    }
    
    const handleSizeChange = (val) => {
      pageSize.value = val
      loadTags()
    }
    
    const handleCurrentChange = (val) => {
      currentPage.value = val
      loadTags()
    }
    
    // 初始化加载
    onMounted(() => {
      loadTags()
    })
    
    return {
      tags,
      total,
      currentPage,
      pageSize,
      dialogVisible,
      dialogTitle,
      form,
      tagForm,
      rules,
      handleCreate,
      handleEdit,
      handleDelete,
      handleSave,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
/* 可以添加样式 */
</style>