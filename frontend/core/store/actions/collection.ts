import { createAsyncThunk } from '@reduxjs/toolkit'
import { BASE_URL } from '../common/index'
import axios from 'axios'

interface MyKnownError {
  errorMessage: string
}

// interface memoAttributes {
//   diaryDate: string
//   memoList: any
// }

export const getCollectionAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('collection/getCollection', async (params, thunkAPI) => {
  try {
    const res = await axios.get(BASE_URL + `/filter/${params}`, {
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
        // diaryDate: params.diaryDate,
        // memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})
export const getCollectionMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('collectionMemo/getCollectionMemo', async (params, thunkAPI) => {
  try {
    const res = await axios.get(BASE_URL + `/filter/preview?memoTypeSeq=${params.memoTypeSeq}&memoSeqList=${params.memoSeqList}`, {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      console.log(res.data.data)
      return res.data.data
    } else if (res.data.status == 204) {
      // 불러올 정보가 없을때
      return {
        // diaryDate: params.diaryDate,
        // memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})