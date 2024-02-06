<template>
  <div>
    <!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Vue.js Web Page</title>
  
</head>
<body>
  <div id="app">
    <!-- Your content goes here -->
    <h1>Welcome to My Vue.js Web Page</h1>
    <!-- Add more elements as needed -->
  </div>

  <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
  <script>
    new Vue({
      el: '#app',
      data: {
        // Your data properties go here
      },
      methods: {
        // Your methods go here
      },
      mounted() {
        // Any code you want to run after the Vue app has been mounted
      }
    });
  </script>
</body>
</html>

  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
    body {
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background-color: #f0f0f0;
    }

    #app {
      width: 1280px;
      height: 720px;
      border: 1px solid #ccc;
      background-color: #fff;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }

</style>