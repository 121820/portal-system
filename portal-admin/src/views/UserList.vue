<template>
  <div class="user-list">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button class="button" type="primary" @click="handleCreate">新建用户</el-button>
        </div>
      </template>
      
      <!-- 用户列表 -->
      <el-table :data="users" style="width: 100%">
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="roleName" label="角色" width="120" />
        <el-table-column prop="statusText" label="状态" width="100" />
        <el-table-column prop="lastLoginAt" label="最后登录" width="180" />
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
            <el-button size="small" @click="handleResetPassword(scope.row)">重置密码</el-button>
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
      <el-form :model="form" label-width="80px" :rules="rules" ref="userForm">
        <el-form-item label="用户名" prop="username" :required="!form.id">
          <el-input v-model="form.username" placeholder="请输入用户名" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色">
            <el-option
              v-for="role in roles"
              :key="role.id"
              :label="role.name"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-text="启用" inactive-text="禁用" />
        </el-form-item>
        <el-form-item v-if="!form.id" label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item v-if="!form.id" label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="请确认密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 重置密码对话框 -->
    <el-dialog v-model="resetPasswordVisible" title="重置密码" width="400px" destroy-on-close>
      <el-form :model="resetPasswordForm" label-width="80px" :rules="resetPasswordRules" ref="resetPasswordFormRef">
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="resetPasswordForm.newPassword" type="password" placeholder="请输入新密码" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="resetPasswordForm.confirmPassword" type="password" placeholder="请确认密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetPasswordVisible = false">取消</el-button>
          <el-button type="primary" @click="handleConfirmResetPassword">确定</el-button>
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
    const users = ref([])
    const roles = ref([])
    const total = ref(0)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const dialogVisible = ref(false)
    const resetPasswordVisible = ref(false)
    const dialogTitle = ref('')
    const currentUserId = ref(null)
    
    const form = ref({
      id: null,
      username: '',
      nickname: '',
      email: '',
      roleId: null,
      status: true,
      password: '',
      confirmPassword: ''
    })
    
    const resetPasswordForm = ref({
      newPassword: '',
      confirmPassword: ''
    })
    
    const userForm = ref(null)
    const resetPasswordFormRef = ref(null)
    
    const rules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
      ],
      nickname: [
        { required: true, message: '请输入昵称', trigger: 'blur' },
        { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
      ],
      email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
      ],
      roleId: [
        { required: true, message: '请选择角色', trigger: 'change' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 30, message: '长度在 6 到 30 个字符', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请确认密码', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur' }
      ]
    }
    
    const resetPasswordRules = {
      newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 6, max: 30, message: '长度在 6 到 30 个字符', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请确认密码', trigger: 'blur' },
        { validator: validateResetConfirmPassword, trigger: 'blur' }
      ]
    }
    
    function validateConfirmPassword(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== form.value.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    
    function validateResetConfirmPassword(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== resetPasswordForm.value.newPassword) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    
    const loadUsers = async () => {
      try {
        const response = await axios.get('/api/v1/users', {
          params: {
            page: currentPage.value,
            size: pageSize.value
          }
        })
        users.value = response.data.data.map(user => ({
          ...user,
          roleName: user.role?.name || '未知',
          statusText: user.status ? '启用' : '禁用'
        }))
        total.value = response.data.total
      } catch (error) {
        console.error('加载用户失败:', error)
        ElMessage.error('加载用户失败')
      }
    }
    
    const loadRoles = async () => {
      try {
        const response = await axios.get('/api/v1/roles')
        roles.value = response.data.data || []
      } catch (error) {
        console.error('加载角色失败:', error)
        ElMessage.error('加载角色失败')
      }
    }
    
    const handleCreate = () => {
      dialogTitle.value = '新建用户'
      form.value = {
        id: null,
        username: '',
        nickname: '',
        email: '',
        roleId: null,
        status: true,
        password: '',
        confirmPassword: ''
      }
      dialogVisible.value = true
    }
    
    const handleEdit = (row) => {
      dialogTitle.value = '编辑用户'
      form.value = {
        id: row.id,
        username: row.username,
        nickname: row.nickname,
        email: row.email,
        roleId: row.roleId,
        status: row.status
      }
      dialogVisible.value = true
    }
    
    const handleDelete = (row) => {
      ElMessageBox.confirm(`确定要删除用户 "${row.username}" 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/v1/users/${row.id}`)
          ElMessage.success('删除成功')
          loadUsers()
        } catch (error) {
          console.error('删除用户失败:', error)
          ElMessage.error('删除用户失败')
        }
      }).catch(() => {})
    }
    
    const handleResetPassword = (row) => {
      currentUserId.value = row.id
      resetPasswordForm.value = {
        newPassword: '',
        confirmPassword: ''
      }
      resetPasswordVisible.value = true
    }
    
    const handleConfirmResetPassword = async () => {
      try {
        await resetPasswordFormRef.value.validate()
        
        await axios.post(`/api/v1/users/${currentUserId.value}/reset-password`, {
          newPassword: resetPasswordForm.value.newPassword
        })
        
        ElMessage.success('密码重置成功')
        resetPasswordVisible.value = false
      } catch (error) {
        console.error('重置密码失败:', error)
        ElMessage.error('重置密码失败')
      }
    }
    
    const handleSave = async () => {
      try {
        await userForm.value.validate()
        
        if (form.value.id) {
          // 更新用户
          const updateData = {
            nickname: form.value.nickname,
            email: form.value.email,
            roleId: form.value.roleId,
            status: form.value.status
          }
          await axios.put(`/api/v1/users/${form.value.id}`, updateData)
          ElMessage.success('用户更新成功')
        } else {
          // 创建用户
          await axios.post('/api/v1/users', form.value)
          ElMessage.success('用户创建成功')
        }
        
        dialogVisible.value = false
        loadUsers()
      } catch (error) {
        console.error('保存用户失败:', error)
        ElMessage.error('保存用户失败')
      }
    }
    
    const handleSizeChange = (val) => {
      pageSize.value = val
      loadUsers()
    }
    
    const handleCurrentChange = (val) => {
      currentPage.value = val
      loadUsers()
    }
    
    // 初始化加载
    onMounted(async () => {
      await loadRoles()
      loadUsers()
    })
    
    return {
      users,
      roles,
      total,
      currentPage,
      pageSize,
      dialogVisible,
      resetPasswordVisible,
      dialogTitle,
      form,
      resetPasswordForm,
      userForm,
      resetPasswordFormRef,
      rules,
      resetPasswordRules,
      handleCreate,
      handleEdit,
      handleDelete,
      handleResetPassword,
      handleConfirmResetPassword,
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