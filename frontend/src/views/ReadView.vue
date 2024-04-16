<script setup lang="js">

import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter()

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

const post = ref({
  id: 0,
  title: "",
  content: ""
})

axios.get(`/api/posts/${props.postId}`).then((response) => {
  post.value = response.data;
});

const moveToEdit = () => {
  router.push({name: "edit", params: {postId: props.postId}})
}
</script>

<template>
  <el-row>
    <el-col>
      <h2>{{ post.title }}</h2>
      <div class="sub d-flex" >
        <div class="category">개발</div>
        <div class="regDate">2024-04-16</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <p>{{ post.content }}</p>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.2rem;
  color: #383838;
  font-weight: 600;
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 12px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}
.sub {
  margin-top: 0;
  font-size: 0.78rem;
  .regDate {
    margin-left: 10px;
    color: #6b6b6b
  }
}
</style>