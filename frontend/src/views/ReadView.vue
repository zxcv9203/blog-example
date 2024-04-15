<script setup lang="js">

import {ref} from "vue";
import axios from "axios";

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
</script>

<template>
  <h2>{{ post.title }}</h2>
  <p>{{ post.content }}</p>
</template>