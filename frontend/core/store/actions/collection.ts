import { createAsyncThunk } from '@reduxjs/toolkit'
import { BASE_URL } from '../common/index'
import axios from 'axios'

interface MyKnownError {
  errorMessage: string
}

// interface memoAttributes {
//   diaryDate: string
//   memoList: any
// }

export const getCollectionAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('collection/getCollection', async (params, thunkAPI) => {
  try {
    const res = await axios.get(BASE_URL + `/filter/${params}`, {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      return res.data.data
    } else if (res.data.status == 204) {
      // 불러올 정보가 없을때
      return {
        // diaryDate: params.diaryDate,
        // memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})
export const getCollectionMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('collectionMemo/getCollectionMemo', async (params, thunkAPI) => {
  try {
    console.log(params)
    const res = await axios.get(BASE_URL + `/filter/preview?memoTypeSeq=${params.memoTypeSeq}&memoSeqList=${params.memoSeqList}`, {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      console.log(res.data.data)
      return res.data.data
    } else if (res.data.status == 204) {
      // 불러올 정보가 없을때
      return {
        // diaryDate: params.diaryDate,
        // memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})

export const getCollectionMemoListAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('collectionMemoList/getCollectionMemoList', async (params, thunkAPI) => {
  try {
    let temp = [];
    console.log(typeof params.type)
    const res = await axios.get(BASE_URL + `/filter`, {
      params: {
        keyword: params.keyword,
        type: params.type
      },
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      console.log(res.data.data)
      if(res.data.data.monthFilter !== undefined){
        
        res.data.data.monthFilter.map((dto)=>{
            dto.monthMemoListDto.map((memo)=>{
                let type = '';
                if(memo.memoTypeSeq === 1) type = '텍스트 +';
                else if(memo.memoTypeSeq === 2) type = '가계부 +';
                else if(memo.memoTypeSeq === 3) type = '체크리스트 +'
                temp.push({title: type+memo.count, date: dto.diaryDate, dateProp: dto.diaryDate, memoTypeSeq: memo.memoTypeSeq, memoSeqList: memo.memoSeqList})
            })
            
        })
        console.log(temp)
    }
    return temp;
      // return res.data.data
    } else if (res.data.status == 204) {
      // 불러올 정보가 없을때
      return {
        // diaryDate: params.diaryDate,
        // memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
})