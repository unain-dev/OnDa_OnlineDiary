import { createAsyncThunk } from '@reduxjs/toolkit'

interface MyKnownError {
  errorMessage: string
}

interface memoAttributes {
  date: string
  memoList: any
}

export const getMemoAction = createAsyncThunk<
  memoAttributes,
  number,
  { rejectValue: MyKnownError }
>('memo/getMemo', async (params, thunkAPI) => {
  console.log(params)
  // api get 요청
  const res = {
    date: '2022-04-28',
    memoList: [
      {
        id: 0,
        width: 200,
        height: 200,
        x: 10,
        y: 40,
        memoTypeSeq: 1,
        info: {
          header: 'test',
          content: 'content',
        },
      },
      {
        id: 1,
        width: 200,
        height: 200,
        x: 120,
        y: 410,
        memoTypeSeq: 1,
        info: {
          header: 'test',
          content: 'content',
        },
      },
    ],
  }

  // [
  //   {
  //     id: 0,
  //     width: 200,
  //     height: 200,
  //     x: 10,
  //     y: 40,
  //     memoTypeSeq: 1,
  //     info: {
  //       header: 'test',
  //       content: 'content',
  //     },
  //   },
  //   {
  //     id: 0,
  //     width: 200,
  //     height: 200,
  //     x: 120,
  //     y: 410,
  //     memoTypeSeq: 3,
  //     info: {
  //     },
  //   },
  // ]

  // 요청 response가지고 reducer에 return
  return res
})

export const setMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/setMemo', async (params, thunkAPI) => {
  // api post 요청
  const res = {
    status: 200,
  }
  console.log(params)

  // 요청 response가지고 reducer에 return
  return res
})
