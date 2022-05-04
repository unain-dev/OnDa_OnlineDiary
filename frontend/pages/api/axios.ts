import axios from 'axios';

// axios 객체 생성
function createInstance() {
  return axios.create({
    baseURL: "http://k6a107.p.ssafy.io/api/v1",
    headers: {
      'Content-type': 'application/json',
    },
  });
}

export const instance = createInstance();
