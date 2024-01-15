const express = require('express')
const app = express()

// respond with "hello world" when a GET request is made to the homepage
app.get('/', function (req, res) {
  res.send('hello world')
})

// GET method route
app.get('/', function (req, res) {
  res.send('GET request to the homepage')
})

module.exports = router
