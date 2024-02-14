<script setup>
import router from '@/router'
import { ref, onBeforeMount, watch } from 'vue'
import { useRoute } from 'vue-router'

const props = defineProps({ privilege: String })
const privilege = props.privilege
const changePrivilege = ref(true)
const route = useRoute()
const sideButtons = ref([])
let showToggle

// 클릭하면 Button List에서 해당 버튼 색 변경하고 라우터링크 이동하는 로직
const changeView = (link, event) => {
  const clickedButton = event.target.innerText
  sideButtons.value.forEach((button) => {
    if (button.name === clickedButton) button.css = 'selected-button'
    else button.css = ''
  })
  router.push({ name: link })
}

// 코치일때 토글 누르면 버튼 변경
watch(
  () => changePrivilege.value,
  () => {
    switch (changePrivilege.value) {
      case true: {
        sideButtons.value = [
          { name: '포트폴리오', link: 'Desktop-5-2', css: 'selected-button' },
          { name: '코칭관리', link: 'Desktop-5-3' },
          { name: '라이브관리', link: 'Desktop-5-5' },
          { name: '영상관리', link: 'Desktop-5-7' }
        ]
        router.push({ name: 'Desktop-5-2' })
        break
      }
      case false: {
        sideButtons.value = [
          { name: '정보수정', link: 'Desktop-6', css: 'selected-button' },
          { name: '코칭일정', link: 'Desktop-7' },
          { name: '회원탈퇴', link: 'Desktop-10' }
        ]
        router.push({ name: 'Desktop-6' })
        break
      }
    }
  }
)

onBeforeMount(() => {
  switch (privilege) {
    case 'COAME': {
      showToggle = false
      sideButtons.value = [
        { name: '코치등록', link: 'Desktop-5-1' },
        { name: '정보수정', link: 'Desktop-6' },
        { name: '코칭일정', link: 'Desktop-7' },
        { name: '회원탈퇴', link: 'Desktop-10' }
      ]

      // 라우터 파라미터로 css 변경
      switch (route.path.split('/')[2]) {
        case 'regist':
          sideButtons.value[0].css = 'selected-button'
          break
        case 'profile':
          sideButtons.value[1].css = 'selected-button'
          break
        case 'coaching':
          sideButtons.value[2].css = 'selected-button'
          break
        case 'resign':
          sideButtons.value[3].css = 'selected-button'
          break
      }
      break
    }
    case 'COACH': {
      showToggle = true
      sideButtons.value = [
        { name: '포트폴리오', link: 'Desktop-5-2' },
        { name: '코칭관리', link: 'Desktop-5-3' },
        { name: '라이브관리', link: 'Desktop-5-5' },
        { name: '영상관리', link: 'Desktop-5-7' }
      ]

      // 라우터 파라미터로 css 변경
      if (route.path.split('/')[1] === 'mypage-coach')
        switch (route.path.split('/')[2]) {
          case 'portfolio':
            sideButtons.value[0].css = 'selected-button'
            break
          case 'coaching':
            sideButtons.value[1].css = 'selected-button'
            break
          case 'live':
            sideButtons.value[2].css = 'selected-button'
            break
        }
      break
    }
    case 'ADMIN': {
      showToggle = false
      sideButtons.value = [
        { name: '권한요청', link: 'Desktop-12', css: 'selected-button' },
        { name: '회원목록', link: 'Desktop-11' }
      ]
      break
    }
  }
})
</script>
<template>
  <div class="sidebar">
    <q-toggle v-if="showToggle" color="primary" v-model="changePrivilege" icon="img:/assets/icons/1.png" size="100px" />
    <div
      v-for="button in sideButtons"
      :key="button.name"
      class="sidebar-button shadow-3"
      :class="button.css"
      @click="changeView(button.link, $event)"
    >
      <p>{{ button.name }}</p>
    </div>
  </div>
</template>
<style scoped>
p {
  margin: 0;
  font-size: 1rem;
  letter-spacing: 0.1rem;
}
.sidebar {
  width: 12%;
  height: 70vh;
  margin: auto;
  min-width: 90px;
}
.sidebar-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 4.4vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.selected-button {
  background-color: #034c8c;
  opacity: 90%;
  color: #fff;
}
.sidebar-button:hover {
  background-color: #034c8c;
  opacity: 100%;
  color: #fff;
  cursor: pointer;
}
.side-toggle {
  display: flex;
  justify-content: center;
}
</style>
