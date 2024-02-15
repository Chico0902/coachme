import { ref } from 'vue'
import { defineStore } from 'pinia'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getDmsByMemberId, getDmsByRoomId, getMyDmRooms } from '@/utils/api/dm-api'

export const useChatStore = defineStore('chatStatus', () => {
  const useDmWindow = ref(false)
  const roomId = ref('')
  const myLongId = ref('')
  const coachId = ref('')
  const coachName = ref('김코치')
  const coachImageUrl = ref('https://cdn.quasar.dev/img/avatar2.jpg')
  const chatList = ref([])
  /**
   * dm 리스트
   * 프로필사진, 이름, 마지막 메시지
   */

  const chats = ref([])

  // 채팅목록 가져오기
  async function openChatList() {
    // 로그인 여부 체크
    const auth = sessionStorage.getItem('auth')
    if (auth != undefined) {
      const isLogin = JSON.parse(auth).isLogin
      if (isLogin === false) {
        Swal.fire({
          title: '로그인이 필요합니다.',
          icon: 'warning',
        })
        return
      }
    } else {
      Swal.fire({
        title: '로그인이 필요합니다.',
        icon: 'warning',
      })
      return
    }
    myLongId.value = decodeToken(getAccessToken()).longId
    // 채팅목록 가져오기 api
    getMyDmRooms(
      myLongId.value,
      (success) => {
        chatList.value = success.data.list
      },
      (fail) => console.error(fail)
    )
  }

  // 채팅목록에서 1개 선택
  function openChatByRoomId(getRoomId, getCoachName, getProfileImg) {
    roomId.value = getRoomId
    coachName.value = getCoachName
    coachImageUrl.value = getProfileImg
    getDmsByRoomId(
      getRoomId,
      (success) => {
        chats.value = success.data.list
      },
      (fail) => console.error(fail)
    )
    useDmWindow.value = true
  }

  // 문의하기
  function openChatByMemberId(getCoachId, getCoachName, getProfileImg) {
    // 로그인 여부 체크
    const auth = sessionStorage.getItem('auth')
    if (auth != undefined) {
      const isLogin = JSON.parse(auth).isLogin
      if (isLogin === false) {
        Swal.fire({
          title: '로그인이 필요합니다.',
          icon: 'warning',
        })
        return
      }
    } else {
      Swal.fire({
        title: '로그인이 필요합니다.',
        icon: 'warning',
      })
      return
    }
    myLongId.value = decodeToken(getAccessToken()).longId
    coachId.value = getCoachId
    coachName.value = getCoachName
    coachImageUrl.value = getProfileImg

    getDmsByMemberId(
      myLongId.value,
      coachId.value,
      (success) => {
        chats.value = success.data.dmList
        roomId.value = success.data.roomId
      },
      (fail) => console.error(fail)
    )
    useDmWindow.value = true
  }

  return {
    useDmWindow,
    chatList,
    chats,
    roomId,
    myLongId,
    coachId,
    coachName,
    coachImageUrl,
    openChatList,
    openChatByRoomId,
    openChatByMemberId
  }
})
