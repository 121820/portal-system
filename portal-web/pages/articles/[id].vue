<template>
  <div class="min-h-screen bg-gray-50">
    <header class="bg-white shadow">
      <div class="max-w-4xl mx-auto px-4 py-6 sm:px-6 lg:px-8">
        <NuxtLink to="/" class="text-blue-600 hover:text-blue-800">&larr; 返回首页</NuxtLink>
      </div>
    </header>
    <main>
      <article class="max-w-4xl mx-auto px-4 py-8 sm:px-6 lg:px-8">
        <header class="mb-8">
          <div class="flex items-center text-sm text-gray-500 mb-4">
            <span>{{ formatDate(article.createdAt) }}</span>
            <span class="mx-2">•</span>
            <span>浏览 {{ article.viewCount || 0 }} 次</span>
          </div>
          <h1 class="text-4xl font-bold text-gray-900 mb-4">{{ article.title }}</h1>
          <div class="flex flex-wrap gap-2 mb-6">
            <span 
              v-for="tag in article.tags" 
              :key="tag.id"
              class="px-3 py-1 bg-blue-100 text-blue-800 text-sm rounded-full"
            >
              {{ tag.name }}
            </span>
          </div>
        </header>
        <div class="prose prose-lg max-w-none">
          <div v-html="article.content"></div>
        </div>
      </article>
    </main>
  </div>
</template>

<script setup>
const route = useRoute()
const { data: article } = await useFetch(`/api/articles/${route.params.id}`, {
  baseURL: useRuntimeConfig().public.apiUrl,
  transform: (data) => {
    return {
      ...data,
      tags: data.articleTags?.map(at => at.tag) || []
    }
  }
})

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.prose {
  color: #1f2937;
}
.prose h2 {
  margin-top: 2rem;
  margin-bottom: 1rem;
  font-size: 1.5rem;
  font-weight: 600;
}
.prose p {
  margin-bottom: 1rem;
  line-height: 1.75;
}
</style>