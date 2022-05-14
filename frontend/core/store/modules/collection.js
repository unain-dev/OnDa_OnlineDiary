import { createSlice } from '@reduxjs/toolkit'
import {
  getCollectionAction,
  getCollectionMemoAction,
} from '../actions/collection'

let initialCollection = {
  collectionInfo: [],
  collectionMemoInfo: [],
}

const collectionSlice = createSlice({
  name: 'collectionInfo',
  initialState: initialCollection,
  reducers: {
    addCollection: (state, action) => {
      state.collectionInfo = action.payload
    },
    addCollectionMemo: (state, action) => {
      state.collectionMemoInfo = action.payload
    },
  },
  extraReducers: (builder) =>
    builder
      .addCase(getCollectionMemoAction.fulfilled, (state, action) => {
        state.collectionMemoInfo = action.payload
        console.log(state.collectionMemoInfo)
      })
      .addCase(getCollectionAction.fulfilled, (state, action) => {
        // console.log(action.payload)
        alert(action.payload.data.msg)
      }),
})

export const { addCollection, addCollectionMemo } = collectionSlice.actions
export default collectionSlice.reducer
