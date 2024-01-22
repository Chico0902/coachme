<script setup>
import { RouterLink } from 'vue-router'
</script>
<template>
  <ul class="nav nav-tabs" id="myTab" role="tablist">
    <span class="navbar-brand ps-4 pe-4">
      <img src="../assets/img/logo.png" alt="Bootstrap" width="50" height="40" />
    </span>
    <li class="nav-item" role="presentation">
      <RouterLink to="/">
        <button
          class="nav-link active"
          id="home-tab"
          data-bs-toggle="tab"
          data-bs-target="#home-tab-pane"
          type="button"
          role="tab"
          aria-controls="home-tab-pane"
          aria-selected="false"
        >
          Home
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/proposal">
        <button
          class="nav-link"
          id="proposal-tab"
          data-bs-toggle="tab"
          data-bs-target="#proposal-tab-pane"
          type="button"
          role="tab"
          aria-controls="proposal-tab-pane"
          aria-selected="false"
        >
          기획서
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/dic">
        <button
          class="nav-link"
          id="dic-tab"
          data-bs-toggle="tab"
          data-bs-target="#dic-tab-pane"
          type="button"
          role="tab"
          aria-controls="dic-tab-pane"
          aria-selected="false"
        >
          용어사전
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/spec">
        <button
          class="nav-link"
          id="spec-tab"
          data-bs-toggle="tab"
          data-bs-target="#spec-tab-pane"
          type="button"
          role="tab"
          aria-controls="spec-tab-pane"
          aria-selected="false"
        >
          명세
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/wireframe">
        <button
          class="nav-link"
          id="wireframe-tab"
          data-bs-toggle="tab"
          data-bs-target="#wireframe-tab-pane"
          type="button"
          role="tab"
          aria-controls="wireframe-tab-pane"
          aria-selected="false"
        >
          화면명세
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/api">
        <button
          class="nav-link"
          id="api-tab"
          data-bs-toggle="tab"
          data-bs-target="#api-tab-pane"
          type="button"
          role="tab"
          aria-controls="api-tab-pane"
          aria-selected="false"
        >
          API
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/git">
        <button
          class="nav-link"
          id="git-tab"
          data-bs-toggle="tab"
          data-bs-target="#git-tab-pane"
          type="button"
          role="tab"
          aria-controls="git-tab-pane"
          aria-selected="false"
        >
          깃
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/agile">
        <button
          class="nav-link"
          id="agile-tab"
          data-bs-toggle="tab"
          data-bs-target="#agile-tab-pane"
          type="button"
          role="tab"
          aria-controls="agile-tab-pane"
          aria-selected="false"
        >
          애자일
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/convention">
        <button
          class="nav-link"
          id="convention-tab"
          data-bs-toggle="tab"
          data-bs-target="#convention-tab-pane"
          type="button"
          role="tab"
          aria-controls="convention-tab-pane"
          aria-selected="false"
        >
          컨벤션
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/server">
        <button
          class="nav-link"
          id="server-tab"
          data-bs-toggle="tab"
          data-bs-target="#server-tab-pane"
          type="button"
          role="tab"
          aria-controls="server-tab-pane"
          aria-selected="false"
        >
          서버
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/erd">
        <button
          class="nav-link"
          id="erd-tab"
          data-bs-toggle="tab"
          data-bs-target="#git-tab-pane"
          type="button"
          role="tab"
          aria-controls="erd-tab-pane"
          aria-selected="false"
        >
          ERD
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/minutes">
        <button
          class="nav-link"
          id="minutes-tab"
          data-bs-toggle="tab"
          data-bs-target="#git-tab-pane"
          type="button"
          role="tab"
          aria-controls="minutes-tab-pane"
          aria-selected="false"
        >
          회의록
        </button>
      </RouterLink>
    </li>
  </ul>
</template>
<style scoped>
a {
  text-decoration-line: none;
  color: black;
}
</style>
