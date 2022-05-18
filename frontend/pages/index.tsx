import type { NextPage } from 'next'
import styles from 'styles/scss/Main.module.scss'
import Login from './user/login'
import Intro from '../component/main/Intro'

const Home: NextPage = () => {
  return (
    <div className={styles.container}>
      <div className={styles.sideContainer}>
        <div className={styles.introContainer}>
          <Intro />
        </div>
      </div>
      <div className={styles.sideContainer}>
        <Login />
      </div>
    </div>
  )
}

export default Home
