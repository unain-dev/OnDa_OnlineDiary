import React, { Component, useState, useEffect, forwardRef } from 'react'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import RND from 'component/diary/RND'
import Pannel from 'component/diary/pannel'
import { useSelector, useDispatch } from 'react-redux'
import { changeMemoState, addMemo, deleteMemo } from 'core/store/modules/diary'
import {
  getMemoAction,
  setMemoAction,
  deleteDayDiary,
} from 'core/store/actions/memo'
import { AppDispatch } from 'core/store'
import Image from 'next/image'
import styles from './diary.module.scss'
import hamburgerIcon from 'public/asset/image/diaryImage/hamburgerIcon.png'
import { useRouter } from 'next/router'
import { calNextDate, calPrevDate } from 'core/common/date'
import DatePickerModule from 'component/diary/DatePickerModule/DatePickerModule'
import moment from 'moment'
import { getDiaryDays } from 'core/api/diary'
import { getCookie } from 'core/common/cookie'

const diary = ({ diaryDate }) => {
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

  const token = getCookie('member')

  const onClickSave = () => {
    const params = {
      param: todaysInfo,
      token: token.jwtToken,
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

  const [goDate, setGoDate] = useState(diaryDate)

  const setTodaysInfo = (date) => {
    if (date != null && date != undefined) {
      const params = {
        diaryDate: date,
        token: token.jwtToken,
      }
      appDispatch(getMemoAction(params))
      setViewSize({
        width: window.innerWidth,
        height: window.innerHeight,
      })
    }
  }

  useEffect(() => {
    setTodaysInfo(goDate)
  }, [goDate])

  const onClickDelete = (date) => {
    // await setGoDate(date)
    const params = {
      diaryDate: date,
      token: token,
    }
    appDispatch(deleteDayDiary(params))
    // router.reload()
    // alert('삭제완료')
  }

  return (
    <>
      <div className={styles.dateContainer}>
        <span>
          <button
            onClick={async () => {
              const date = calPrevDate(diaryDate)
              await setGoDate(date)
              router.push(`/diary/${date}`)
            }}
          >
            &lt;
          </button>
          <span>
            <DatePickerModule
              startDate={Date.parse(goDate)}
              setStartDate={(date) => {
                const d = moment(date).format('YYYY-MM-DD')
                setGoDate(d)
                router.push(`/diary/${d}`)
              }}
            />
          </span>
          <button
            onClick={async () => {
              const date = calNextDate(diaryDate)
              await setGoDate(calNextDate(diaryDate))
              router.push(`/diary/${date}`)
            }}
          >
            &gt;
          </button>
          <button
            className={styles.deleteBtn}
            onClick={async () => {
              // await setGoDate(date)
              onClickDelete(goDate)
            }}
          >
            삭제하기
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

export async function getServerSideProps(context) {
  return {
    props: { diaryDate: context.params.diaryDate },
  }
}

export default diary
