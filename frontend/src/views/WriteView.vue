<script setup lang="js">

import {ref} from "vue";

import axios from 'axios';

const title = ref("")
const content = ref("")

const write = function () {
  axios.post("/api/posts", {
    "title": title.value,
    "content": content.value
  })
}
</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력하세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows=15 placeholder="내용을 입력하세요"></el-input>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="write()">저장</el-button>
  </div>
</template>

<style scoped>

</style>