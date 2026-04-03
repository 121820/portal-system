import axios from 'axios'

// 创建文件API实例
const fileApi = axios.create({
  baseURL: '/api/v1/files',
  timeout: 30000
})

/**
 * 上传文件到NAS
 * @param {File} file - 要上传的文件
 * @returns {Promise} 上传结果
 */
export const uploadFile = async (file) => {
  const formData = new FormData()
  formData.append('file', file)
  
  try {
    const response = await fileApi.post('/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    return response.data
  } catch (error) {
    console.error('文件上传失败:', error)
    throw error
  }
}

/**
 * 批量上传文件
 * @param {File[]} files - 文件数组
 * @returns {Promise} 上传结果数组
 */
export const uploadFiles = async (files) => {
  const uploadPromises = files.map(file => uploadFile(file))
  return Promise.all(uploadPromises)
}