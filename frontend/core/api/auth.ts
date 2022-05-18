import { BASE_URL } from 'core/store/common'
import axios from 'axios'

export const getIsMember = async (token) => {
  console.log(token)
  try {
    const res = await axios.get(BASE_URL + `/members/check`, {
      headers: {
        Authorization: `Bearer ` + token,
        'Content-Type': 'application/json',
      },
    })
    if (res.data.status == 200) {
      return true
    } else {
      alert('정보를 불러오는 데에 문제가 생겼습니다.')
      return false
    }
  } catch (error) {
    alert('정보를 불러오는 데에 문제가 생겼습니다.')
    return false
  }
}
