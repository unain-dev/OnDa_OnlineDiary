import React, { Children } from 'react'
import { Rnd } from 'react-rnd'

interface RndProps {
  content: {
    width: string
    height: string
    x: number
    y: number
  }
  disableDragging: boolean
  onResize: (e, direction, ref, delta, position) => void
  style?: React.CSSProperties
  children: JSX.Element
  onDragStop: (e, d) => void
  onResizeStop: (e, direction, ref, delta, position) => void
}

const RND = (props: RndProps) => {
  const {
    content,
    onDragStop,
    onResize,
    onResizeStop,
    style,
    disableDragging,
  } = props
  return (
    <Rnd
      style={style}
      size={{ width: content.width, height: content.height }}
      position={{ x: content.x, y: content.y }}
      onDragStop={(e, d) => onDragStop(e, d)}
      onResize={(e, direction, ref, delta, position) =>
        onResize(e, direction, ref, delta, position)
      }
      onResizeStop={(e, direction, ref, delta, position) =>
        onResizeStop(e, direction, ref, delta, position)
      }
      disableDragging={disableDragging}
    >
      {props.children}
    </Rnd>
  )
}

// <Rnd
//   key={index}
//   // style={test}
//   size={{ width: c.width, height: c.height }}
//   position={{ x: c.x, y: c.y }}
//   onDragStop={(e, d) => {
//     setContent(
//       content.map((con, idx) =>
//         idx === index ? { ...con, x: d.x, y: d.y } : con,
//       ),
//     )
//     // setContent({ ...content, x: d.x, y: d.y })
//   }}
//   onResizeStop={(e, direction, ref, delta, position) => {
//     setContent(
//       content.map((con, idx) =>
//         idx === index
//           ? { ...con, width: ref.style.width, height: ref.style.height }
//           : con,
//       ),
//     )
//     // setContent({
//     //   ...content,
//     //   width: ref.style.width,
//     //   height: ref.style.height,
//     //   ...position,
//     // })
//   }}
// >
//   <MemoFrameTest
//     width={Number(c.width.substring(0, c.width.length - 2))}
//     height={Number(c.height.substring(0, c.height.length - 2))}
//     content={'helloWorld'}
//     header={'this is header'}
//   />
// </Rnd>
export default RND
