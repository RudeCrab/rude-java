<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
  // 整体布局样式，让整个视图都铺满
  html, body, #app {
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
  }
</style>
