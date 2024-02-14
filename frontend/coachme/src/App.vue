<template>
  <div id="app">
    <RouterView />
  </div>
</template>

<script setup></script>


<style>
body::-webkit-scrollbar {
  width: 10px;
  height: 0.5rem;
}

body::-webkit-scrollbar-thumb {
  background-color: #6593ff;
  border-radius: 1.5rem;
  min-width: 50px;
}

body::-webkit-scrollbar-thumb:hover {
  background-color: #3370ff;
}

body::-webkit-scrollbar-track {
  background-color: #c7c7c7;
  border-radius: 1.5rem;
}
</style>
