import { createSlice } from '@reduxjs/toolkit'
import { getMemoAction, setMemoAction } from '../actions/memo'

// interface Memo {
//   width: number
//   height: number
//   x: number
//   y: number
// }

let initialMemo = {
  diaryDate: '',
  lastId: 0,
  memoList: [],
}

const diarySlice = createSlice({
  name: 'diaryInfo',
  initialState: initialMemo,
  reducers: {
    addMemo: (state, action) => {
      state.memoList.push(action.payload)
      state.lastId = action.payload.id
    },
    changeMemoState: (state, action) => {
      let arr = state.memoList.map((memo) =>
        memo.id === action.payload.id
          ? {
              ...memo,
              // width: action.payload.width,
              // height: action.payload.height,
              // x: action.payload.x,
              // y: action.payload.y,
              // info: action.payload.info,
              // isEditing: action.payload.isEditing,
              ...action.payload,
            }
          : memo,
      )
      console.log(arr)
      state.memoList = arr
    },
    deleteMemo: (state, action) => {
      const id = action.payload
      let arr = state.memoList.filter((memo) => memo.id !== id)
      state.memoList = arr
    },
  },
  extraReducers: (builder) =>
    builder
      .addCase(getMemoAction.fulfilled, (state, action) => {
        const list = action.payload.memoList
        state.diaryDate = action.payload.diaryDate
        if (list.length > 0) state.lastId = list[list.length - 1].id
        else state.lastId = 0
        list.map((memo) => state.memoList.push(memo))
      })
      .addCase(setMemoAction.fulfilled, (state, action) => {
        console.log(action.payload)
      }),
})

export const { addMemo, changeMemoState, deleteMemo } = diarySlice.actions
export default diarySlice.reducer
