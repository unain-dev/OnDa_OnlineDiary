import Link from 'next/link'
import styles from 'component/common/header/Header.module.scss'
import Router from 'next/router'
import logo from 'public/asset/image/logo/onda_logo.png'
import onda from 'public/asset/image/logo/onda.png'
import Image from 'next/image'
import { dateToString } from 'core/common/date'

const menus = [
  { name: '월별 모아보기', url: '/collection/month' },
  { name: '오늘의 다이어리', url: `/diary/${dateToString(new Date())}` },
]

const Header = () => {
  return (
    <div className={styles.naviWrapper}>
      <div className={styles.logo}>
        <Image
          src={onda}
          height="80"
          width="130"
          onClick={() => Router.push('/')}
        />
      </div>
      <div className={styles.menuContainer}>
        {menus.map((m) => (
          <div
            className={styles.menu}
            onClick={() =>
              // document.location.href = m.url
              Router.push(m.url)
            }
          >
            {m.name}
          </div>
        ))}
        <div className={styles.auth}>
          {/* 토큰 여부 검사 후 선택적 렌더링 */}
          <div
            className={styles.menu}
            onClick={() => Router.push('/user/login')}
          >
            로그인
          </div>
          <div
            className={styles.menu}
            onClick={() => {
              //로그아웃 로직 추가 필요
            }}
          >
            로그아웃
          </div>
        </div>
      </div>
    </div>
  )
}

export default Header
