// import {Cookies} from 'react-cookie'

import { getCookie, getCookies, removeCookies, setCookies } from "cookies-next";

// const cookies = new Cookies();

// export const setCookie = (name: string, value: string, option?: any) => {
//   return cookies.set(name, value, { ...option })
// }

// export const getCookie = (name: string) => {
//   return cookies.get(name)
// }

// export const removeCookie = (name: string, option?: any) => {
//   return cookies.remove(name, { ...option })
// }

export const getServerSideProps = ({ req, res }) => {
  setCookies('test', 'value', { req, res});
  getCookie('test', { req, res});
  getCookies({ req, res});
  removeCookies('test', { req, res});
return { props: {}};
}
// export function getServerSideProps({ req, res }) {
//   return { props: { token: req.cookies.token } };
// }

// import SsrCookie from "ssr-cookie";

// const cookie = new SsrCookie();

// // Set cookie
// cookie.set("user", {userName: "test", token: "some token here"}, { expires: 30 });

// // Retrieve cookie
// const user = cookie.get("user");

// // Clear cookies
// cookie.remove("user");