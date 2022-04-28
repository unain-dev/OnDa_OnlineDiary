import React, { useState, useRef } from 'react'
import styles from './Pannel.module.scss'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import textMemoImg from 'public/asset/image/memoImage/textMemoImg.png'
import Image from 'next/image'

const Pannel = ({ onClick }) => {
  const memoSeqList = [1, 2, 3]

  return (
    <div className={styles.pannel}>
      {memoSeqList.map((seq) => (
        <div className={styles.container}>
          <Image src={textMemoImg} className="image" width="" height="" />
          <div className={styles.middle}>
            <button
              className={styles.button}
              onClick={(e) => {
                onClick(seq, e)
              }}
            >
              {seq}번
            </button>
          </div>
        </div>
      ))}
    </div>
  )
}

export default Pannel
