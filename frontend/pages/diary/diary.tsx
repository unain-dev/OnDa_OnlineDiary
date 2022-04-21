import React, { useState } from 'react'
import { Rnd } from 'react-rnd'
import MemoFrameTest from 'component/memo/memoCommon/MemoFrameTest'

const diary = () => {
  const [content, setContent] = useState({
    width: '200px',
    height: '200px',
    x: 10,
    y: 10,
  })

  const test = {
    background: '#898989',
  } as const

  console.log('x: ', content.x, 'y: ', content.y)
  console.log('width: ', content.width, 'height: ', content.height)

  return (
    <>
      <Rnd
        style={test}
        size={{ width: content.width, height: content.height }}
        position={{ x: content.x, y: content.y }}
        onDragStop={(e, d) => {
          setContent({ ...content, x: d.x, y: d.y })
        }}
        onResizeStop={(e, direction, ref, delta, position) => {
          setContent({
            ...content,
            width: ref.style.width,
            height: ref.style.height,
            ...position,
          })
        }}
      >
        <MemoFrameTest
          width={Number(content.width.substring(0, content.width.length - 2))}
          height={Number(
            content.height.substring(0, content.height.length - 2),
          )}
          content={'helloWorld'}
          header={'this is header'}
        />
      </Rnd>
    </>
  )
}

export default diary
