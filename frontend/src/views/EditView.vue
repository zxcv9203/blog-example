<script setup lang="js">
import axios from "axios";
import {useRouter} from "vue-router";
import {ref} from "vue";

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

const edit = function () {
  axios.patch(`/api/posts/${props.postId}`, post.value)
      .then(() => {
        router.replace({name: "Home"})
      })
}
</script>

<template>
  <div>
    <el-input v-model="post.title"/>
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows=15></el-input>
  </div>

  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정</el-button>
  </div>
</template>

<style scoped>

</style>