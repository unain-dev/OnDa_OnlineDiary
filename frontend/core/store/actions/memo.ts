import { createAsyncThunk } from '@reduxjs/toolkit'
import { BASE_URL } from '../common/index'
import axios from 'axios'

interface MyKnownError {
  errorMessage: string
}

interface memoAttributes {
  diaryDate: string
  memoList: any
}

export const getMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/getMemo', async (params, thunkAPI) => {
  try {
    const res = await axios.get(BASE_URL + `/diary/${params.diaryDate}`, {
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
        diaryDate: params.diaryDate,
        memoList: [],
      }
    }
  } catch (error) {
    console.log(error)
  }
  // api get 요청
  // const res = {
  //   diaryDate: '2022-04-28',
  //   memoList: [
  //     {
  //       id: 0,
  //       width: 200,
  //       height: 200,
  //       x: 10,
  //       y: 40,
  //       memoTypeSeq: 1,
  //       info: {
  //         header: 'test',
  //         content: 'content',
  //       },
  //       isEditing: false,
  //     },
  //     // {
  //     //   id: 1,
  //     //   width: 200,
  //     //   height: 200,
  //     //   x: 310,
  //     //   y: 40,
  //     //   memoTypeSeq: 2,
  //     //   info: [
  //     //     {
  //     //       content: '테스트 비용',
  //     //       income: '10000',
  //     //       outcome: '20000',
  //     //     },
  //     //   ],
  //     //   isEditing: false,
  //     // },
  //     {
  //       id: 2,
  //       width: 200,
  //       height: 200,
  //       x: 610,
  //       y: 40,
  //       memoTypeSeq: 3,
  //       info: {
  //         checklistHeader: 'this is checklist header',
  //         checklistItems: [
  //           {
  //             isChecked: true,
  //             content: 'this is checklist item text 1',
  //           },
  //           {
  //             isChecked: false,
  //             content: 'this is checklist item text 2',
  //           },
  //           {
  //             isChecked: true,
  //             content: 'this is checklist item text 3',
  //           },
  //         ],
  //       },
  //       isEditing: false,
  //     },
  //     // {
  //     //   id: 3,
  //     //   width: 200,
  //     //   height: 200,
  //     //   x: 10,
  //     //   y: 340,
  //     //   memoTypeSeq: 4,
  //     //   info: {},
  //     //   isEditing: false,
  //     // },
  //     // {
  //     //   id: 4,
  //     //   width: 200,
  //     //   height: 200,
  //     //   x: 310,
  //     //   y: 340,
  //     //   memoTypeSeq: 5,
  //     //   info: '😘',
  //     //   isEditing: false,
  //     // },
  //   ],
  // }
})

function transForm(param) {
  console.log(param)

  const formData = new FormData()
  let files = [];
  let numbering = 0;

  param.memoList.forEach((memo)=>{
      if(memo.memoTypeSeq===4){
        files.push(memo.info);
      }
    })
  
  let arr = param.memoList.map((memo) =>
  memo.memoTypeSeq === 4
    ? {
        width: memo.width,
        height: memo.height,
        x: memo.x,
        y: memo.y,
        isEditing: memo.isEditing,
        id: memo.id,
        memoTypeSeq: memo.memoTypeSeq,
        info: numbering++,
      }
    : memo,
  )
  const newParam = {diaryDate: param.diaryDate, lastId: param.lastId, memoList: arr}
  console.log(typeof numbering)
  console.log(newParam)
  console.log(param)
  files.forEach((file)=> console.log(file))
  const p = JSON.stringify(newParam)
  const blob = new Blob([p], { type: 'application/json' })
  formData.append('reqDiaryDto', blob)
  files.forEach((file)=> formData.append('files', file))
  return formData
}

export const setMemoAction = createAsyncThunk<
  any,
  any,
  { rejectValue: MyKnownError }
>('memo/setMemo', async (params, thunkAPI) => {
  // api post 요청
  try {
    const res = await axios.post(BASE_URL + '/diary', transForm(params.param), {
      headers: {
        Authorization: `Bearer ` + params.token,
        'Content-Type': 'multipart/form-data',
      },
    })
    if (res.data.status == 201) {
      return res
    }
  } catch (error) {
    console.log(error)
  }
})
