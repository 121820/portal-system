<template>
  <div class="min-h-screen bg-gray-50">
    <header class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 py-6 sm:px-6 lg:px-8">
        <h1 class="text-3xl font-bold text-gray-900">门户网站</h1>
      </div>
    </header>
    <main>
      <div class="max-w-7xl mx-auto px-4 py-6 sm:px-6 lg:px-8">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <article 
            v-for="article in articles" 
            :key="article.id"
            class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
          >
            <div class="p-6">
              <div class="flex items-center text-sm text-gray-500 mb-2">
                <span>{{ formatDate(article.createdAt) }}</span>
                <span class="mx-2">•</span>
                <span>{{ article.categoryName }}</span>
              </div>
              <h2 class="text-xl font-semibold text-gray-900 mb-3">{{ article.title }}</h2>
              <p class="text-gray-600 mb-4">{{ article.summary }}</p>
              <div class="flex flex-wrap gap-2">
                <span 
                  v-for="tag in article.tags" 
                  :key="tag.id"
                  class="px-2 py-1 bg-blue-100 text-blue-800 text-xs rounded-full"
                >
                  {{ tag.name }}
                </span>
              </div>
            </div>
          </article>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
const { data: articles } = await useFetch('/api/articles', {
  baseURL: useRuntimeConfig().public.apiUrl,
  transform: (data) => {
    return data.data.map(article => ({
      ...article,
      categoryName: article.category?.name || '未分类',
      tags: article.articleTags?.map(at => at.tag) || []
    }))
  }
})

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}
</script>