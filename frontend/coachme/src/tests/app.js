// const socket = io()
const myFace = document.getElementById('myFace')
const muteBtn = document.getElementById('microphone-button')
const cameraBtn = document.getElementById('camera-button')

let myStream
let cameraOff = false
let muted = false

async function getMedia() {
  try {
    myStream = await navigator.mediaDevices.getUserMedia({
      audio: true,
      video: true
    })
    myFace.srcObject = myStream
    console.log(myStream)
  } catch (e) {
    console.log(e)
  }
}

getMedia()

function handleMuteClick() {
  if (!muted) {
    muteBtn.innerText = '마이크 켜기'
    muted = !muted
  } else {
    muteBtn.innerHTML = '마이크 끄기'
    muted = !muted
  }
}
function handleCameraClick() {
  if (!cameraOff) {
    cameraBtn.innerText = '카메라 켜기'
    cameraOff = !cameraOff
  } else {
    cameraBtn.innerHTML = '카메라 끄기'
    cameraOff = !cameraOff
  }
}

muteBtn.addEventListener('click', handleMuteClick)
cameraBtn.addEventListener('click', handleCameraClick)
