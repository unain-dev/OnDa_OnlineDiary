import {
  AnyAction,
  combineReducers,
  configureStore,
  getDefaultMiddleware,
} from '@reduxjs/toolkit'
import { HYDRATE, createWrapper } from 'next-redux-wrapper'
import diary from './modules/diary'
import collection from './modules/collection'
import { useDispatch } from 'react-redux'

// axios.defaults.baseURL = getConfig().publicRuntimeConfig.apiServerUrl

export const reducer = (state, action: AnyAction) => {
  if (action.type === HYDRATE) {
    return {
      ...state,
      ...action.payload,
    }
  }
  return combineReducers({
    diary,
    collection,
  })(state, action)
}

const makeStore = () =>
  configureStore({
    reducer,
    middleware: [
      ...getDefaultMiddleware({ thunk: true, serializableCheck: false }),
    ],
  })

export const wrapper = createWrapper(makeStore, {
  debug: process.env.NODE_ENV !== 'production',
})

const store = configureStore({
  reducer,
})

export type RootState = ReturnType<typeof reducer>
export type AppDispatch = typeof store.dispatch
export const useAppDispatch = () => useDispatch<AppDispatch>()
