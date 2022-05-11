import axios from 'axios'
import { BASE_URL } from 'core/store/common'

export const getDiaryDays = async ({ token, diaryDate }, success) => {
  try {
    const res = await axios.get(BASE_URL + `/diary/calendar/${diaryDate}`, {
      headers: {
        Authorization: `Bearer ` + token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      success(res.data.data.days)
    } else {
      alert('정보를 불러오는 데에 문제가 생겼습니다.')
    }
  } catch (error) {
    console.log(error)
  }
}
