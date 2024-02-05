import { ref, reactive } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('chatStatus', () => {
  const isChatList = ref(true) // dm 리스트가 표시되는지
  const showChat = ref(false) // dm창 자체가 열려있는지

  const chatList = reactive([{
    img: "https://cdn.quasar.dev/img/avatar2.jpg",
    name: "Mary",
    lastDm: "Hello",
  },
  {
    img: "https://cdn.quasar.dev/img/avatar4.jpg",
    name: "John",
    lastDm: "Your suggestion is considered.",
  },
  {
    img: "https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg",
    name: "White Cat",
    lastDm: "meow",
  }])
  /* dm 리스트
  프로필사진, 이름, 마지막 메시지
  */

  const directMessage = reactive([{
    "id": "coame",
    "chat": ["hey, how are you?", "I'm so so these day"]
  }, {
    "id": "coach",
    "chat": ["doing fine, how r you?"]
  }, {
    "id": "coach",
    "chat": ["I just feel like typing a really, really, REALLY long message to annoy you..."]
  }])
  /* 주고받은 dm 내용
  예시로 작성되었으며, 1:1 리스트에 표시될 내역
  */
  
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

  return { selectDm, backToList, closeDmWindow, resetDm, requestDm, isChatList, showChat, chatList, directMessage } 
})
