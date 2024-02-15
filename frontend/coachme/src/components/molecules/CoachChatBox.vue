<script setup>
import Labels from '../atoms/CardLabel.vue'
import Like from '../atoms/CustomLike.vue'
import CustomButton from '../atoms/CustomButton.vue'
import { onBeforeMount, computed, ref } from 'vue'
import { useChatStore } from '../../stores/chat-status.js'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getLikeCoach, deleteLikeCoach, getCheckCoachLike } from '@/utils/api/like-api'

const store = useChatStore()
const { openChatByMemberId } = store
// 채팅 관련 store

const props = defineProps({
  coach: {
    // 코치 이름
    type: String
  },
  coachId: {
    type: Number
  },
  profileImg: {
    type: String
  }
})

const chatLabel = computed(() => props.coach + '님께 문의해보세요.')
const likeLabel = computed(() => props.coach + '님이 마음에 드셨나요?')
// 코치이름에 따라 반응형으로 변경

const myLongId = ref()
const likeState = ref(false)

onBeforeMount(() => {
  myLongId.value = decodeToken(getAccessToken()).longId

  new Promise((resolve, reject) =>
    getCheckCoachLike(
      myLongId.value,
      props.coachId,
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
    deleteLikeCoach(
      myLongId.value,
      props.coachId,
      (success) => {
        console.log(success)
      },
      (fail) => console.error(fail)
    )
    likeState.value = !likeState.value
  } else {
    getLikeCoach(
      myLongId.value,
      props.coachId,
      (success) => {
        console.log(success)
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
        <Labels label="궁금한 내용이 있으신가요?" class="card-margin"></Labels>
        <Labels :label="chatLabel" class="card-margin" style="color: #004c98"></Labels>
        <!-- 침콩 버튼과 채팅하기 버튼 -->
        <q-item-section>
          <div class="buttons card-margin">
            <CustomButton
              style="width: 100px; height: 20px; background-color: #fcbf17; color: black"
              @click="openChatByMemberId(props.coachId, props.coach, props.profileImg)"
              >채팅하기</CustomButton
            >
          </div>
        </q-item-section>

        <q-separator style="margin-top: 2vh"></q-separator>

        <Labels :label="likeLabel" class="card-margin"></Labels>
        <Labels label="좋아요 버튼을 클릭해주세요!  " class="card-margin" style="color: #004c98"></Labels>

        <q-item-section>
          <div class="buttons card-margin">
            <Like :like="likeState" @click="changeState"></Like>
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
