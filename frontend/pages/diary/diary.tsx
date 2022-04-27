import React, { Component, useState } from 'react'
import MemoSeparator from 'component/memo/memoSeparator/MemoSeparator'
import RND from 'component/diary/RND'

const diary = () => {
  // 컴포넌트(떡메)의 위치, 크기 정보
  // 추 후에 고유번호(백엔드와 협의 후 결정)값이 추가되어야 함.
  const [content, setContent] = useState([
    {
      width: '400px',
      height: '400px',
      x: 10,
      y: 10,
      content: 'helloWorld',
      memoTypeSeq: 1,
    },
    {
      width: '400px',
      height: '400px',
      x: 40,
      y: 310,
      content: '',
      memoTypeSeq: 2,
    },
    {
      width: '400px',
      height: '400px',
      x: 80,
      y: 210,
      content: [],
      memoTypeSeq: 3,
    },
  ])
  const [draggableState, setDraggableState] = useState(Array(content.length).fill(true));
  const test = {
    background: '#898989',
    overflow: 'hidden',
  } as const

  console.log(content)

  const enableDragging = (index) => {
    draggableState[index]=true;
    setDraggableState([...draggableState]);
    console.log("enable dragging")
  }
  const disableDragging = (index) =>{
    draggableState[index]=false;
    setDraggableState([...draggableState]);
    console.log("disable dragging")
  }

  return (
    <>
      {content.map((c, index) => (
        <RND
          style={test}
          content={c}
          key={index}
          onDragStop={(e, d) => {
            console.log(d)
            setContent(
              content.map((con, idx) =>
                idx === index ? { ...con, x: d.x, y: d.y } : con,
              ),
            )
          }}
          onResizeStop={(e, direction, ref, delta, position) => {
            setContent(
              content.map((con, idx) =>
                idx === index
                  ? { ...con, width: ref.style.width, height: ref.style.height }
                  : con,
              ),
            )
          }}
          onResize={(e, direction, ref, delta, position) => {
            setContent(
              content.map((con, idx) =>
                idx === index
                  ? { ...con, width: ref.style.width, height: ref.style.height }
                  : con,
              ),
            )
          }}
          disableDragging={!draggableState[index]}
        >
          {/* 여기에 이런식으로 넣고자하는 컴포넌트 넣기*/}
          <MemoSeparator
            width={Number(c.width.substring(0, c.width.length - 2))}
            height={Number(c.height.substring(0, c.height.length - 2))}
            content={c.content}
            header={'this is header'}
            memoTypeSeq={c.memoTypeSeq}
            drag={{enableDragging: ()=>enableDragging(index), disableDragging: ()=> disableDragging(index)}}
          />
        </RND>
      ))}
    </>
  )
}

export default diary
