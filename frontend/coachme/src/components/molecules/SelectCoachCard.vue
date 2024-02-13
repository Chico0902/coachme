<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import Button from '@/components/atoms/CustomButton.vue'
import router from '@/router'
import { ref } from 'vue'
import { useChatStore } from '../../stores/chat-status'

/**
 * VARIABLES
 */

// in pinia
const chatStore = useChatStore()
const { openChatByMemberId } = chatStore

// in props
const props = defineProps({
  id: Number,
  coachName: String,
  coaching: String,
  rating: String,
  reviewCount: String,
  img: String
})

// for render
const stars = ref(props.rating)
</script>

<template>
  <!-- 프로필 사진 영역 -->
  <q-item>
    <q-item-section avatar style="padding-right: 0px" class="flex flex-center">
      <profile :img="props.img" size="135px"></profile>
    </q-item-section>
  </q-item>

  <!-- 코치 이름 영역 -->
  <q-item style="margin-top: -1vh">
    <q-item-section>
      <labels :label="`${props.coachName}`" class="text-black text-bold" style="padding: -2vw"></labels>
    </q-item-section>
  </q-item>

  <!-- 제공 코칭과 별점 영역-->
  <q-item class="justify-evenly" >
    <!-- 공간 분리-->
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
    <!-- 별점 영역 -->
    <q-item-section style="min-width: fit-content">
      <q-rating size="18px" v-model="stars" :max="5" color="orange" readonly></q-rating>
      <span style="font-size: 15px; color: black; font-weight: 600">{{ rating !== null ? Math.round(rating * 10) / 10 : 0 }} ({{ reviewCount }}) </span>
    </q-item-section>
  </q-item>

  <!-- 대표 코칭 이름 -->
  <q-item class="justify-evenly">
    <!-- 코칭 이름 -->
    <q-item-section style="min-width: fit-content">
      <labels :label="`${props.coaching}`" class="text-black text-bold"></labels>
    </q-item-section>
  </q-item>

  <!-- 코치 버튼 영역 -->
  <q-item class="coach-button">
    <q-item-section vertical>
      <Button
        label="상세보기"
        style="background-color: #fcbf17; color: black"
        @click="router.push(`/search/coach/detail/${props.id}`)"
      ></Button>
    </q-item-section>
    <q-item-section>
      <Button
        label="채팅하기"
        style="background-color: #1a66da; color: white"
        @click="openChatByMemberId(props.id, props.coachName, props.img)"
      ></Button>
    </q-item-section>
  </q-item>
</template>

<style scoped></style>
