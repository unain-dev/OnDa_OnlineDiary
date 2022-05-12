import { createAsyncThunk } from '@reduxjs/toolkit'
import { BASE_URL } from '../common/index'
import axios from 'axios'

interface MyKnownError {
  errorMessage: string
}

interface memoAttributes {
  diaryDate: string
  memoList: any
}

export const getMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/getMemo', async (params, thunkAPI) => {
  try {
    const res = await axios.get(BASE_URL + `/diary/${params.diaryDate}`, {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      return res.data.data
    } else if (res.data.status == 204) {
      // 불러올 정보가 없을때
      return {
        diaryDate: params.diaryDate,
        memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})

function transForm(param) {
  console.log(param)

  const formData = new FormData()
  let files = []
  let numbering = 0

  param.memoList.forEach((memo) => {
    if (memo.memoTypeSeq === 4) {
      files.push(memo.info)
    }
  })

  param.memoList.forEach((memo)=>{
    console.log(typeof memo.info)
      if(memo.memoTypeSeq===4 && typeof memo.info === 'object'){
        files.push(memo.info);
      }
    })
  
  let arr = param.memoList.map((memo) =>
  // 4번인데 info 부분이 string src 이면 그대로
  memo.memoTypeSeq === 4 && typeof memo.info === 'object'
    ? {
        width: memo.width,
        height: memo.height,
        x: memo.x,
        y: memo.y,
        isEditing: memo.isEditing,
        id: memo.id,
        memoTypeSeq: memo.memoTypeSeq,
        info: numbering++,
      }
    : memo,
  )
  const newParam = {
    diaryDate: param.diaryDate,
    lastId: param.lastId,
    memoList: arr,
  }
  console.log(typeof numbering)
  console.log(newParam)
  console.log(param)
  files.forEach((file) => console.log(file))
  const p = JSON.stringify(newParam)
  const blob = new Blob([p], { type: 'application/json' })
  formData.append('reqDiaryDto', blob)
  files.forEach((file) => formData.append('files', file))
  return formData
}

export const setMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/setMemo', async (params, thunkAPI) => {
  try {
    const res = await axios.post(BASE_URL + '/diary', transForm(params.param), {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'multipart/form-data',
      },
    })
    if (res.data.status == 201 || res.data.status == 400) {
      return res
    }
  } catch (error) {
    console.log(error)
  }
})

export const deleteDayDiary = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/deleteDayDiary', async (params, thunkAPI) => {
  try {
    const res = await axios.delete(BASE_URL + `/diary/${params.diaryDate}`, {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'multipart/form-data',
      },
    })
    return res
  } catch (error) {
    console.log(error)
  }
})
