<script setup>
import Labels from '../atoms/CardLabel.vue'
import CustomButton from '../atoms/CustomButton.vue'
import CustomLike from '../atoms/CustomLike.vue'
import { ref, computed, onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { useChatStore } from '../../stores/chat-status.js'
import { getLikeCoaching, deleteLikeCoaching, getCheckCoachingLike } from '@/utils/api/like-api'

const chatStore = useChatStore()
const { openChatByMemberId } = chatStore

// 채팅 관련 store

const props = defineProps({
  coach: {
    // 코치 이름
    type: String
  },
  coachingId: {
    type: Number
  },
  coachId: {
    type: Number
  },
  profileImg: {
    type: String
  }
})
const chatLabel = computed(() => props.coach + '님께 문의해보세요.')
// 코치이름에 따라 반응형으로 변경

const myLongId = ref()
const likeState = ref(false)

onBeforeMount(() => {
  myLongId.value = decodeToken(getAccessToken()).longId

  new Promise((resolve, reject) =>
    getCheckCoachingLike(
      myLongId.value,
      props.coachingId,
      (success) => {
        likeState.value = success.data.islike
        resolve()
      },
      (fail) => reject(fail)
    )
  )
})

const changeState = () => {
  if (likeState.value === true) {
    deleteLikeCoaching(
      myLongId.value,
      props.coachingId,
      (success) => {
      },
      (fail) => console.error(fail)
    )
    likeState.value = !likeState.value
  } else {
    getLikeCoaching(
      myLongId.value,
      props.coachingId,
      (success) => {
      },
      (fail) => console.error(fail)
    )
    likeState.value = !likeState.value
  }
}
</script>

<template>
  <q-card class="my-card">
    <q-item>
      <q-item-section>
        <!-- 설명 섹션-->
        <q-item-section>
          <Labels label="이 코칭에 관심이 있으신가요?" class="card-margin" style="margin: auto"></Labels>
          <Labels :label="chatLabel" class="card-margin" style="margin: auto; color: #004c98"></Labels>
        </q-item-section>

        <!-- 코칭 신청하기 버튼과 채팅하기 버튼 -->
        <q-item-section>
          <div class="buttons card-margin">
            <CustomButton
              style="width: 6rem; height: 20px; margin-left: 0.5vw; background-color: #004c98; color: white"
              @click="openChatByMemberId(props.coachId, props.coach, props.profileImg)"
              >채팅하기</CustomButton
            >
          </div>
        </q-item-section>

        <q-separator style="margin-top: 2vh"></q-separator>

        <Labels label="이 코칭이 마음에 드셨나요?" class="card-margin"></Labels>
        <Labels label="좋아요 버튼을 클릭해주세요!" class="card-margin" style="color: #004c98"></Labels>

        <q-item-section>
          <div class="buttons card-margin">
            <CustomLike :like="likeState" @click="changeState"></CustomLike>
          </div>
        </q-item-section>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  max-width: max-content;
  text-align: center;
}

.buttons {
  display: flex;
  align-items: center;
  margin: auto;
}

.card-margin {
  padding-top: 2vh;
}
</style>
