import React, { useState, useRef } from 'react'
import styles from './Pannel.module.scss'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import textMemoImg from 'public/asset/image/memoImage/textMemoImg.png'
import textMemo from 'public/asset/image/memoImage/textMemo.png'
import checklistMemo from 'public/asset/image/memoImage/checklistMemo.png'
import financialMemo from 'public/asset/image/memoImage/financialMemo.png'
import imageUploadLogo from 'public/asset/image/memoImage/imageUploadLogo.png'
import stickerMemo from 'public/asset/image/memoImage/stickerMemo.png'

import closeBtnImg from 'public/asset/image/diaryImage/closeBtnImg.png'
import Image from 'next/image'

function getMemo(seq) {
  const obj = {
    id: null,
    width: 200,
    height: 200,
    x: 100,
    y: 200,
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
        info: [],
      }
    case 3:
      return {
        ...obj,
        memoTypeSeq: 3,
        info: {
          checklistHeader: '',
          checklistItems: [],
        },
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

const memoSeqList = [
  { seq: 1, src: textMemo },
  { seq: 2, src: financialMemo },
  { seq: 3, src: checklistMemo },
  { seq: 4, src: imageUploadLogo },
  { seq: 5, src: stickerMemo },
]

const Pannel = ({ onClick, onCloseBtn }) => {
  return (
    <div className={styles.pannel}>
      <div className={styles.closeBtnImgContainer}>
        <Image
          src={closeBtnImg}
          className={styles.closeBtnImg}
          width="36"
          height="36"
          onClick={onCloseBtn}
        />
      </div>
      {memoSeqList.map((memo, index) => (
        <div className={styles.container} key={index}>
          <Image src={memo.src} className="image" width="" height="" />
          <div className={styles.middle}>
            <button
              className={styles.button}
              onClick={(e) => {
                onClick(getMemo(memo.seq), e)
              }}
            >
              {memo.seq}번
            </button>
          </div>
        </div>
      ))}
    </div>
  )
}

export default Pannel
