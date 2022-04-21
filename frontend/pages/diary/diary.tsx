import React, { useState } from 'react'
import { Rnd } from 'react-rnd'
import MemoFrameTest from 'component/memo/memoCommon/MemoFrameTest'

const diary = () => {
  const [content, setContent] = useState([
    {
      width: '200px',
      height: '200px',
      x: 10,
      y: 10,
    },
    {
      width: '500px',
      height: '100px',
      x: 40,
      y: 310,
    },
  ])

  // const test = {
  //   background: '#898989',
  // } as const

  console.log(content)

  return (
    <>
      {content.map((c, index) => (
        <Rnd
          key={index}
          // style={test}
          size={{ width: c.width, height: c.height }}
          position={{ x: c.x, y: c.y }}
          onDragStop={(e, d) => {
            setContent(
              content.map((con, idx) =>
                idx === index ? { ...con, x: d.x, y: d.y } : con,
              ),
            )
            // setContent({ ...content, x: d.x, y: d.y })
          }}
          onResizeStop={(e, direction, ref, delta, position) => {
            setContent(
              content.map((con, idx) =>
                idx === index
                  ? { ...con, width: ref.style.width, height: ref.style.height }
                  : con,
              ),
            )
            // setContent({
            //   ...content,
            //   width: ref.style.width,
            //   height: ref.style.height,
            //   ...position,
            // })
          }}
        >
          <MemoFrameTest
            width={Number(c.width.substring(0, c.width.length - 2))}
            height={Number(c.height.substring(0, c.height.length - 2))}
            content={'helloWorld'}
            header={'this is header'}
          />
        </Rnd>
      ))}
    </>
  )
}

export default diary
