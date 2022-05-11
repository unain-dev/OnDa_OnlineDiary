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
  const formData = new FormData()
  const p = JSON.stringify(param)
  const blob = new Blob([p], { type: 'application/json' })
  formData.append('reqDiaryDto', blob)
  formData.append('files', '')
  return formData
}

export const setMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/setMemo', async (params, thunkAPI) => {
  // api post 요청
  try {
    const res = await axios.post(BASE_URL + '/diary', transForm(params.param), {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'multipart/form-data',
      },
    })
    if (res.data.status == 201) {
      return res
    }
  } catch (error) {
    console.log(error)
  }
})
