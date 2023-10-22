const express = require('express')
const app = express()
const axios = require('axios')

const PORT = 3000

app.use(express.json())
app.use(function (req, res, next) {
  // Website you wish to allow to connect
  res.setHeader('Access-Control-Allow-Origin', '*')

  // Request methods you wish to allow
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')

  // Request headers you wish to allow
  res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type')

  // Set to true if you need the website to include cookies in the requests sent
  // to the API (e.g. in case you use sessions)
  res.setHeader('Access-Control-Allow-Credentials', true)

  // Pass to next layer of middleware
  next()
})

app.post('/api', async (req, res) => {
  console.log('Trigger')
  try {
    if (req) {
      console.log(req.body)
    }

    let axiosConfig = {
      headers: req.body.header,
      url: req.body.url,
      method: req.body.method
    }
    if (axiosConfig.method != 'get') {
      axiosConfig[data] = req.body.restBody
    }

    let response = await axios(axiosConfig)

    console.log(response.headers)

    res.status(200).json({
      header: response.headers,
      data: response.data
    })
  } catch (error) {
    console.log(error)
    res.sendStatus(500).json({ header: response.headers, data: response.data })
  }
})

app.listen(PORT, function () {
  console.log('listening on ', PORT)
})
