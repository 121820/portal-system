<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :default-config="toolbarConfig"
      :mode="mode"
    />
    <Editor
      style="height: 500px; overflow-y: hidden;"
      v-model="valueHtml"
      :default-config="editorConfig"
      :mode="mode"
      @on-created="handleCreated"
      @on-change="handleChange"
    />
  </div>
</template>

<script>
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import axios from 'axios'

export default {
  components: { Editor, Toolbar },
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    mode: {
      type: String,
      default: 'default' // 'default' 或 'simple'
    }
  },
  emits: ['update:modelValue'],
  data() {
    return {
      editorRef: null,
      valueHtml: this.modelValue,
      toolbarConfig: {},
      editorConfig: {
        placeholder: '请输入内容...',
        MENU_CONF: {
          uploadImage: {
            fieldName: 'file',
            maxFileSize: 10 * 1024 * 1024, // 10MB
            maxNumberOfFiles: 10,
            allowedFileTypes: ['image/jpeg', 'image/png', 'image/gif', 'image/webp'],
            customUpload: async (files, insertFn) => {
              for (let file of files) {
                try {
                  const formData = new FormData()
                  formData.append('file', file)
                  
                  const response = await axios.post('/api/v1/files/upload', formData, {
                    headers: {
                      'Content-Type': 'multipart/form-data'
                    }
                  })
                  
                  if (response.data.success) {
                    insertFn(response.data.data.url)
                  } else {
                    console.error('上传失败:', response.data.message)
                    this.$message.error('图片上传失败')
                  }
                } catch (error) {
                  console.error('上传错误:', error)
                  this.$message.error('图片上传出错')
                }
              }
            }
          }
        }
      }
    }
  },
  watch: {
    modelValue(newVal) {
      if (newVal !== this.valueHtml) {
        this.valueHtml = newVal
      }
    },
    valueHtml(newVal) {
      if (newVal !== this.modelValue) {
        this.$emit('update:modelValue', newVal)
      }
    }
  },
  methods: {
    handleCreated(editor) {
      this.editorRef = editor
    },
    handleChange(editor) {
      this.valueHtml = editor.getHtml()
    },
    destroy() {
      if (this.editorRef == null) return
      this.editorRef.destroy()
      this.editorRef = null
    }
  },
  beforeUnmount() {
    this.destroy()
  }
}
</script>

<style src="@wangeditor/editor/dist/css/style.css"></style>