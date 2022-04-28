import { createSlice } from '@reduxjs/toolkit'
import { getMemoAction } from '../actions/memo'

// interface Memo {
//   width: number
//   height: number
//   x: number
//   y: number
// }

let initialMemo = [
  //   {
  //    id: 1
  //     width: 200,
  //     height: 200,
  //     x: 10,
  //     y: 10,
  //     memoTypeSeq: 1,
  //   },
  //   {
  //     id: 1
  //     width: 200,
  //     height: 200,
  //     x: 10,
  //     y: 10,
  //     memoTypeSeq: 2,
  //   },
]

const diarySlice = createSlice({
  name: 'memoList',
  initialState: initialMemo,
  reducers: {
    addMemo: (state, action) => {
      state.push(action.payload)
    },
    changeMemoState: (state, action) => {
      let arr = state.filter((s) => s.id !== action.payload.id)
      arr.push(action.payload)

      return arr
    },
  },
  extraReducers: (builder) =>
    builder.addCase(getMemoAction.fulfilled, (state, action) => {
      // console.log('success')
      // console.log(action.payload)
      state.push(action.payload)
    }),
})

export const { addMemo, changeMemoState } = diarySlice.actions
export default diarySlice.reducer
