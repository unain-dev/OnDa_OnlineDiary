import React, { useState, useRef } from 'react'
import styles from './Pannel.module.scss'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import textMemoImg from 'public/asset/image/memoImage/textMemoImg.png'
import Image from 'next/image'

function getMemo(seq) {
  const obj = {
    id: null,
    width: 200,
    height: 200,
    x: 30,
    y: 30,
    isEditing: false,
  }
  switch (seq) {
    case 1:
      return {
        ...obj,
        memoTypeSeq: 1,
        info: {
          header: '제목',
          content: '내용',
        },
      }
    case 2:
      return {
        ...obj,
        memoTypeSeq: 2,
        info: [
          {
            content: '항목을 입력해주세요',
            income: '',
            outcome: '',
          },
        ],
      }
    case 3:
      return {
        ...obj,
        memoTypeSeq: 3,
        info: [
          {
            content: '항목을 입력해주세요',
            isChecked: false,
          },
        ],
      }
    case 4:
      return {
        ...obj,
        memoTypeSeq: 4,
        info: {},
      }
    case 5:
      return {
        ...obj,
        memoTypeSeq: 5,
        info: '😘',
      }
  }
}

const Pannel = ({ onClick }) => {
  const memoSeqList = [1, 2, 3, 4, 5]

  return (
    <div className={styles.pannel}>
      {memoSeqList.map((seq, index) => (
        <div className={styles.container} key={index}>
          <Image src={textMemoImg} className="image" width="" height="" />
          <div className={styles.middle}>
            <button
              className={styles.button}
              onClick={(e) => {
                onClick(getMemo(seq), e)
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
