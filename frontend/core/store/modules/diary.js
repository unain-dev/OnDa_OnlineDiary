import { createSlice } from '@reduxjs/toolkit'
import { getMemoAction, setMemoAction } from '../actions/memo'

// interface Memo {
//   width: number
//   height: number
//   x: number
//   y: number
// }

let initialMemo = []

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
    builder
      .addCase(getMemoAction.fulfilled, (state, action) => {
        // console.log('success')
        // console.log(action.payload)
        state.push(action.payload)
        // state = action.payload
      })
      .addCase(setMemoAction.fulfilled, (state, action) => {
        console.log(action.payload)
      }),
})

export const { addMemo, changeMemoState } = diarySlice.actions
export default diarySlice.reducer
