<template>
  <div class="category-list">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>栏目管理</span>
          <el-button class="button" type="primary" @click="handleCreate">新建栏目</el-button>
        </div>
      </template>
      
      <!-- 栏目树形结构 -->
      <el-tree
        :data="categories"
        node-key="id"
        :props="{ label: 'name', children: 'children' }"
        :expand-on-click-node="false"
        default-expand-all
        show-checkbox
      >
        <template #default="{ node, data }">
          <span class="custom-tree-node">
            <span>{{ node.label }}</span>
            <span class="tree-node-actions">
              <el-button
                type="text"
                size="small"
                @click="() => handleEdit(data)"
              >
                编辑
              </el-button>
              <el-button
                type="text"
                size="small"
                @click="() => handleDelete(data)"
              >
                删除
              </el-button>
              <el-button
                type="text"
                size="small"
                @click="() => handleAddChild(data)"
              >
                添加子栏目
              </el-button>
            </span>
          </span>
        </template>
      </el-tree>
    </el-card>
    
    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" destroy-on-close>
      <el-form :model="form" label-width="80px" :rules="rules" ref="categoryForm">
        <el-form-item label="栏目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入栏目名称" />
        </el-form-item>
        <el-form-item label="父级栏目">
          <el-cascader
            v-model="form.parentId"
            :options="categoryOptions"
            :props="{ checkStrictly: true, emitPath: false, value: 'id', label: 'name' }"
            placeholder="请选择父级栏目"
            clearable
          />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-text="启用" inactive-text="禁用" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" rows="3" placeholder="请输入栏目描述" />
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
import { ref } from 'vue'
import axios from 'axios'

export default {
  setup() {
    const categories = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('')
    const form = ref({
      id: null,
      name: '',
      parentId: null,
      sortOrder: 0,
      status: true,
      description: ''
    })
    const categoryForm = ref(null)
    
    const rules = {
      name: [
        { required: true, message: '请输入栏目名称', trigger: 'blur' },
        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
      ]
    }
    
    // 获取扁平化栏目选项用于级联选择器
    const getCategoryOptions = (data) => {
      const options = []
      const traverse = (items, level = 0) => {
        items.forEach(item => {
          options.push({
            id: item.id,
            name: '—'.repeat(level) + ' ' + item.name,
            children: []
          })
          if (item.children && item.children.length > 0) {
            traverse(item.children, level + 1)
          }
        })
      }
      traverse(data)
      return options
    }
    
    const categoryOptions = ref([])
    
    const loadCategories = async () => {
      try {
        const response = await axios.get('/api/v1/categories')
        categories.value = response.data.data || []
        categoryOptions.value = getCategoryOptions(categories.value)
      } catch (error) {
        console.error('加载栏目失败:', error)
        ElMessage.error('加载栏目失败')
      }
    }
    
    const handleCreate = () => {
      dialogTitle.value = '新建栏目'
      form.value = {
        id: null,
        name: '',
        parentId: null,
        sortOrder: 0,
        status: true,
        description: ''
      }
      dialogVisible.value = true
    }
    
    const handleEdit = (row) => {
      dialogTitle.value = '编辑栏目'
      form.value = { ...row }
      dialogVisible.value = true
    }
    
    const handleAddChild = (parent) => {
      dialogTitle.value = `添加子栏目 - ${parent.name}`
      form.value = {
        id: null,
        name: '',
        parentId: parent.id,
        sortOrder: 0,
        status: true,
        description: ''
      }
      dialogVisible.value = true
    }
    
    const handleDelete = (row) => {
      ElMessageBox.confirm(`确定要删除栏目 "${row.name}" 吗？删除后将无法恢复！`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/v1/categories/${row.id}`)
          ElMessage.success('删除成功')
          loadCategories()
        } catch (error) {
          console.error('删除栏目失败:', error)
          ElMessage.error('删除栏目失败')
        }
      }).catch(() => {})
    }
    
    const handleSave = async () => {
      try {
        await categoryForm.value.validate()
        
        if (form.value.id) {
          // 更新栏目
          await axios.put(`/api/v1/categories/${form.value.id}`, form.value)
          ElMessage.success('栏目更新成功')
        } else {
          // 创建栏目
          await axios.post('/api/v1/categories', form.value)
          ElMessage.success('栏目创建成功')
        }
        
        dialogVisible.value = false
        loadCategories()
      } catch (error) {
        console.error('保存栏目失败:', error)
        ElMessage.error('保存栏目失败')
      }
    }
    
    // 初始化加载
    loadCategories()
    
    return {
      categories,
      dialogVisible,
      dialogTitle,
      form,
      categoryForm,
      rules,
      categoryOptions,
      handleCreate,
      handleEdit,
      handleAddChild,
      handleDelete,
      handleSave
    }
  }
}