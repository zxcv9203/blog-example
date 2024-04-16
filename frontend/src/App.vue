<script setup lang="js">
import {RouterView} from 'vue-router';
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
</script>

<template>
  <Header />
  <Content />
</template>

<style>
body {
  margin: 0;
  width: 100%;
  font-family: 'NotoMono Nerd Font', sans-serif;
}

</style>