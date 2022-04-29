import { createSlice } from '@reduxjs/toolkit'
import { getMemoAction, setMemoAction } from '../actions/memo'

// interface Memo {
//   width: number
//   height: number
//   x: number
//   y: number
// }

let initialMemo = {
  date: '',
  memoList: [],
}

const diarySlice = createSlice({
  name: 'diaryInfo',
  initialState: initialMemo,
  reducers: {
    addMemo: (state, action) => {
      state.memoList.push(action.payload)
    },
    changeMemoState: (state, action) => {
      let arr = state.memoList.filter((s) => s.id !== action.payload.id)
      arr.push(action.payload)

      state.memoList = arr
    },
  },
  extraReducers: (builder) =>
    builder
      .addCase(getMemoAction.fulfilled, (state, action) => {
        // console.log('success')
        // console.log(action.payload)
        // state.push(action.payload)
        // state = action.payload
        console.log(action)
        state.date = action.payload.date
        action.payload.memoList.map((memo) => state.memoList.push(memo))
      })
      .addCase(setMemoAction.fulfilled, (state, action) => {
        console.log(action.payload)
      }),
})

export const { addMemo, changeMemoState } = diarySlice.actions
export default diarySlice.reducer
