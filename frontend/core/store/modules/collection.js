import { createSlice } from '@reduxjs/toolkit'
import {
  getCollectionAction,
  getCollectionMemoAction,
  getCollectionMemoListAction,
} from '../actions/collection'

let initialCollection = {
  collectionInfo: [],
  collectionMemoInfo: [],
  collectionMemoListInfo: [],
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
      })
      .addCase(getCollectionMemoListAction.fulfilled, (state, action) => {
        state.collectionMemoListInfo = action.payload
        console.log(state.collectionMemoListInfo)
      }),
})

export const { addCollection, addCollectionMemo } = collectionSlice.actions
export default collectionSlice.reducer
