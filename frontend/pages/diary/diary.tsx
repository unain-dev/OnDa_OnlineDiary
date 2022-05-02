import React, { Component, useState, useEffect } from 'react'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import RND from 'component/diary/RND'
import Pannel from 'component/diary/pannel'
import { useSelector, useDispatch } from 'react-redux'
import { changeMemoState, addMemo } from 'core/store/modules/diary'
import { getMemoAction, setMemoAction } from 'core/store/actions/memo'
import { AppDispatch } from 'core/store'

const diary = () => {
  const value = useSelector(({ diary }) => diary)
  console.log(value)
  const len = value.memoList.length

  const dispatch = useDispatch()
  const appDispatch: AppDispatch = useDispatch() // 추가됨.

  const [draggableState, setDraggableState] = useState(Array(len).fill(true))

  // const test = {
  //   background: '#898989',
  //   overflow: 'hidden',
  // } as const

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
    dispatch(addMemo({ ...params, id: len }))
    // alert('추가되었습니다.')
  }

  console.log('reload')

  const memberSeq = 3

  useEffect(() => {
    appDispatch(getMemoAction(memberSeq)) //수정
  }, [])

  useEffect(() => {
    setDraggableState(Array(len).fill(true))
  }, [len])

  const onClickSave = () => {
    appDispatch(setMemoAction(value)) //수정
  }

  return (
    <>
      <button onClick={onClickSave}>저장하기</button>
      {value.memoList.map((c, index) => (
        <RND
          style={{
            // background: '#898989',
            // background: '#ffc',
            // background: 'transparent',
            background: `${c.memoTypeSeq === 5 ? 'transparent' : '#ffc'}`,
            borderRadius: '10px',
            boxShadow: '0 5px 5px `rgba(0,0,0,0.4)`',
            borderStyle: `${c.isEditing ? 'dashed' : 'none'}`,
            // overflow: 'hidden',
          }}
          content={c}
          key={index}
          onDragStop={(e, d) => {
            dispatch(
              changeMemoState({
                ...c,
                x: d.x,
                y: d.y,
              }),
            )
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
          {/* 여기에 이런식으로 넣고자하는 컴포넌트 넣기*/}
          <MemoSeparator
            width={c.width}
            height={c.height}
            memoInfo={c} // memoInfo = memoList의 한 요소 전체 정보(width, height, x, y, info(content, header))
            memoTypeSeq={c.memoTypeSeq}
            drag={{
              enableDragging: () => enableDragging(index),
              disableDragging: () => disableDragging(index),
            }}
          />
        </RND>
      ))}
      <Pannel onClick={onClickPannel} />
    </>
  )
}

export default diary
