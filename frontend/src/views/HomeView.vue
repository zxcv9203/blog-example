<script setup lang="js">
import axios from "axios";
import {ref} from "vue";

const posts = ref([]);
axios.get("/api/posts").then((response) => {
  posts.value = response.data;
});

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        <router-link :to="{name: 'read', params: {postId: post.id}}">
          {{ post.title }}
        </router-link>
      </div>
      <div>{{ post.content }}</div>
    </li>
  </ul>
</template>

<style scoped>
li {
  margin-bottom: 1rem;
}

li:last-child {
  margin-bottom: 0;
}
</style>