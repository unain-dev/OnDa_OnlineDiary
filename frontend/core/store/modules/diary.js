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
      // let arr = state.memoList.filter((s) => s.id !== action.payload.id)
      // arr.push(action.payload)

      let arr = state.memoList.map((memo) =>
        memo.id === action.payload.id
          ? {
              ...memo,
              width: action.payload.width,
              height: action.payload.height,
              x: action.payload.x,
              y: action.payload.y,
              info: action.payload.info,
              isEditing: action.payload.isEditing,
            }
          : memo,
      )

      state.memoList = arr
    },
    // memoText 수정시 dispatch 되는 리듀서
    changeText: (state, action) => {
      let arr = state.memoList.map((memo) =>
        memo.id === action.payload.id
          ? {
              ...memo,
              info: action.payload.info,
            }
          : memo,
      )

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

export const { addMemo, changeMemoState, changeText } = diarySlice.actions
export default diarySlice.reducer
