import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('chatStatus', () => {
  const isChatList = ref(true) // dm 리스트가 표시되는지
  const showChat = ref(false) // dm창 자체가 열려있는지
  
  const selectDm = () => {
    isChatList.value = !isChatList.value
  } // dm리스트에서 하나의 방을 골랐을 때
  
  const backToList = () => {
    isChatList.value = !isChatList.value
  } // 1:1dm에서 홈버튼을 눌렀을 때
  
  const closeDmWindow = () => {
    showChat.value = !showChat.value
  } // 어느 창이든 닫기 버튼을 눌렀을 때
  
  const resetDm = () => {
    isChatList.value = true
  } // dm 창 자체가 닫혔을 때, 리스트로 돌아가도록 설정
  
  const requestDm = () => {
    showChat.value = true
    isChatList.value = true
  } // 코치 카드에서 채팅하기 버튼 클릭 했을 때 작동 버튼

  return { selectDm, backToList, closeDmWindow, resetDm, requestDm, isChatList, showChat } 
})
