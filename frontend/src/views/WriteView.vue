<script setup lang="js">
import {ref} from "vue";

const count = ref(0);
</script>

<template>
  <p>안녕하세요</p>
  <button @click="count++">Click me</button>
  <p>{{ count }}</p>
</template>

<style scoped>

</style>