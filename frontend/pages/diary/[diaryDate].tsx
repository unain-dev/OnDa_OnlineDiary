import React, { Component, useState, useEffect } from 'react'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import RND from 'component/diary/RND'
import Pannel from 'component/diary/pannel'
import { useSelector, useDispatch } from 'react-redux'
import { changeMemoState, addMemo, deleteMemo } from 'core/store/modules/diary'
import { getMemoAction, setMemoAction } from 'core/store/actions/memo'
import { AppDispatch } from 'core/store'
import calendarIcon from 'public/asset/image/diaryImage/calendarIcon.png'
import Image from 'next/image'
import styles from './diary.module.scss'
import closeBtnImg from 'public/asset/image/diaryImage/closeBtnImg.png'
import hamburgerIcon from 'public/asset/image/diaryImage/hamburgerIcon.png'
import { truncate } from 'fs'
import { useRouter } from 'next/router'
import { calNextDate, calPrevDate } from 'core/common/date'

const diary = () => {
  const todaysInfo = useSelector(({ diary }) => diary)
  const len = todaysInfo.memoList.length
  const lastId = todaysInfo.lastId

  const dispatch = useDispatch()
  const appDispatch: AppDispatch = useDispatch()

  const [draggableState, setDraggableState] = useState(Array(len).fill(true))

  const enableDragging = (index) => {
    draggableState[index] = true
    setDraggableState([...draggableState])
    console.log('enable dragging')
  }
  const disableDragging = (index) => {
    draggableState[index] = false
    setDraggableState([...draggableState])
    console.log('disable dragging')
  }

  const onClickPannel = (params, e) => {
    dispatch(addMemo({ ...params, id: lastId + 1 }))
    // alert('추가되었습니다.')
  }

  useEffect(() => {
    setDraggableState(Array(len).fill(true))
  }, [len])

  const token =
    'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MDEiLCJpc3MiOiJvbmRhLnNzYWZ5LmNvbSIsImV4cCI6MTY1MzM1Nzk4NywiaWF0IjoxNjUyMDYxOTg3fQ._yDfuQ4lL5tbYci6CFY-x08muvg71L5wo1uTH6FMMls_2IVep7jGlh5BMVWtqPXYoLp5Zm6UbzRY1aJYagiLrg'

  const onClickSave = () => {
    const params = {
      param: todaysInfo,
      token: token,
    }
    appDispatch(setMemoAction(params))
  }

  const onDeleteMemo = (id) => {
    appDispatch(deleteMemo(id))
  }

  const [viewSize, setViewSize] = useState({
    width: 0,
    height: 0,
  })
  const [pannelIsOpen, setPannelIsOpen] = useState(false)

  const router = useRouter()
  const { diaryDate } = router.query || {}

  console.log('load')
  useEffect(() => {
    if (diaryDate != null && diaryDate != '' && diaryDate != undefined) {
      const params = {
        diaryDate: diaryDate,
        token: token,
      }
      appDispatch(getMemoAction(params))
      setViewSize({
        width: window.innerWidth,
        height: window.innerHeight,
      })
    }
  }, [diaryDate])

  return (
    <>
      <div className={styles.dateContainer}>
        <Image
          src={calendarIcon}
          className={styles.calendarIcon}
          width="40"
          height="40"
        />
        <span>
          <button
            onClick={() => {
              router.push(`/diary/${calPrevDate(diaryDate)}`)
            }}
          >
            &lt;
          </button>
          <span>
            <h2>{todaysInfo.diaryDate}</h2>
          </span>
          <button
            onClick={() => {
              router.push(`/diary/${calNextDate(diaryDate)}`)
            }}
          >
            &gt;
          </button>
        </span>
        <span className={styles.closeBtnImgContainer}>
          {!pannelIsOpen && (
            <Image
              src={hamburgerIcon}
              width="36"
              height="36"
              onClick={(e) => {
                setPannelIsOpen(true)
              }}
            />
          )}
        </span>
      </div>
      <div className={styles.saveBtnWrapper}>
        <button className={styles.saveBtn} onClick={onClickSave}>
          저장하기
        </button>
      </div>
      {todaysInfo.memoList.map((c, index) => (
        <RND
          style={{
            background: `${c.memoTypeSeq === 5 ? 'transparent' : '#ffc'}`,
            borderRadius: '10px',
            boxShadow: '0 5px 5px `rgba(0,0,0,0.4)`',
            borderStyle: `${c.isEditing ? 'dashed' : 'none'}`,
          }}
          content={c}
          key={index}
          onDragStop={(e, d) => {
            if (d.x > 0 && d.y > 0 && d.x < viewSize.width) {
              dispatch(
                changeMemoState({
                  ...c,
                  x: d.x,
                  y: d.y,
                }),
              )
            }
          }}
          onResizeStop={(e, direction, ref, delta, position) => {
            dispatch(
              changeMemoState({
                ...c,
                width: Number(
                  ref.style.width.substring(0, ref.style.width.length - 2),
                ),
                height: Number(
                  ref.style.height.substring(0, ref.style.height.length - 2),
                ),
              }),
            )
          }}
          disableDragging={!draggableState[index]}
        >
          <MemoSeparator
            memoInfo={c} // memoInfo = memoList의 한 요소 전체 정보(width, height, x, y, info(content, header))
            memoTypeSeq={c.memoTypeSeq}
            drag={{
              enableDragging: () => enableDragging(index),
              disableDragging: () => disableDragging(index),
            }}
            onDeleteMemo={onDeleteMemo}
          />
        </RND>
      ))}

      {pannelIsOpen && (
        <Pannel
          onClick={onClickPannel}
          onCloseBtn={() => {
            setPannelIsOpen(false)
          }}
        />
      )}
    </>
  )
}

export default diary
