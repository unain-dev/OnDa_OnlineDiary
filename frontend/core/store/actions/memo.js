import { createAsyncThunk } from '@reduxjs/toolkit'

export const getMemoAction = createAsyncThunk(
  'memo/getMemo',
  async (params, thunkAPI) => {
    // api 요청
    const res = {
      id: 0,
      width: 200,
      height: 200,
      x: 10,
      y: 40,
      memoTypeSeq: 2,
    }

    // 요청 response가지고 reducer에 return
    return res
  },
)
