import React, { Component, useState, useEffect } from 'react'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import RND from 'component/diary/RND'
import Pannel from 'component/diary/pannel'
import { useSelector, useDispatch } from 'react-redux'
import { changeMemoState, addMemo } from 'core/store/modules/diary'
import { getMemoAction, setMemoAction } from 'core/store/actions/memo'

const diary = () => {
  const value = useSelector((state) => state)
  console.log(value)
  const len = value.diary.memoList.length

  const dispatch = useDispatch()

  const [draggableState, setDraggableState] = useState(Array(len).fill(true))

  const test = {
    background: '#898989',
    overflow: 'hidden',
  } as const

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
    dispatch(
      addMemo({
        id: len + 1,
        width: 400,
        height: 200,
        x: 10,
        y: 10,
        memoTypeSeq: params,
        info: {},
      }),
    )
    // alert('추가되었습니다.')
  }

  console.log('reload')

  const memberSeq = 1

  useEffect(() => {
    dispatch(getMemoAction(memberSeq))
  }, [])

  useEffect(() => {
    setDraggableState(Array(len).fill(true))
  }, [len])

  const onClickSave = () => {
    dispatch(setMemoAction(value.diary))
  }

  return (
    <>
      <button onClick={onClickSave}>저장하기</button>
      {value.diary.memoList.map((c, index) => (
        <RND
          style={test}
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
            info={c.info}
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
