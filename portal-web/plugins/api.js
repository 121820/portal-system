export default defineNuxtPlugin((nuxtApp) => {
  const config = useRuntimeConfig()
  
  // 添加 API 客户端
  nuxtApp.$api = {
    get: (url, options = {}) => {
      return $fetch(`${config.public.apiUrl}${url}`, {
        method: 'GET',
        ...options
      })
    },
    post: (url, body, options = {}) => {
      return $fetch(`${config.public.apiUrl}${url}`, {
        method: 'POST',
        body,
        ...options
      })
    },
    put: (url, body, options = {}) => {
      return $fetch(`${config.public.apiUrl}${url}`, {
        method: 'PUT',
        body,
        ...options
      })
    },
    delete: (url, options = {}) => {
      return $fetch(`${config.public.apiUrl}${url}`, {
        method: 'DELETE',
        ...options
      })
    }
  }
})