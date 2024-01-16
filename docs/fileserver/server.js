import express from 'express'
import multer from 'multer'
import cors from 'cors'
import fs from 'fs'
import path from 'path'

const __dirname = path.resolve()
const app = express()
const HOST_IP = '70.12.246.246'
const PORT = 1235

// Cors 설정
const corsOptions = {
  origin: `http://${HOST_IP}:${1234}`,
  credentials: true
}
app.use(cors(corsOptions))

// 저장 형식
const saveForm = function (req, file, cb) {
  const fileName = path.basename(file.originalname, path.extname(file.originalname))
  const ext = path.extname(file.originalname)
  cb(null, 'minutes' + '-' + fileName + ext) // 파일 이름 + '-' + 현재 시간 + 확장자
}

// Multer 설정
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/minutes') // 파일이 저장될 폴더
  },
  filename: saveForm
})

const upload = multer({ storage: storage })

// 정적 파일 서빙 (uploads 폴더에 있는 파일들을 웹에서 접근 가능하게 함)
app.use('/uploads', express.static('uploads'))

// Server Listening
app.get('/', (req, res) => {
  res.status(200).send('Filesystem Server is now Listening!')
})

// 파일목록 보내기
app.get('/uploads/minutes', (req, res) => {
  const uploadPath = path.join(__dirname, 'uploads/minutes')
  fs.readdir(uploadPath, (err, files) => {
    // if (err) {
    //   return res.status(500).send('파일 목록을 가져오는 중에 오류가 발생했습니다.')
    // }

    return res.json({ files })
  })
})

// 파일 업로드 처리
app.post('/uploads/minutes', upload.any(), (req, res) => {
  console.log(req)
  res.status(200).send('회의록이 업로드되었습니다.')
})

// 에러 핸들링 미들웨어
// app.use((err, req, res) => {
//   console.error(err)
//   res.status(500).send('에러코드를 확인하세요!')
// })

app.listen(PORT, () => {
  console.log(`서버가 http://${HOST_IP}:${PORT} 에서 실행 중입니다.`)
})
