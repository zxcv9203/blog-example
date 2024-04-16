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
      <div class="title">
        <router-link :to="{name: 'read', params: {postId: post.id}}">
          {{ post.title }}
        </router-link>
      </div>
      <div class="content">{{ post.content }}</div>
      <div class="sub d-flex" >
        <div class="category">개발</div>
        <div class="regDate">2024-04-16</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.4rem;
    padding: 0;

    .title {
      a {
        font-size: 1.2rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }
    .sub {
      margin-top: 7px;
      font-size: 0.78rem;
      .regDate {
        margin-left: 10px;
        color: #6b6b6b
      }
    }
  }
}

</style>