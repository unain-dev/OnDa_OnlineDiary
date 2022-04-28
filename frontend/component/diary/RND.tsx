import React, { Children } from 'react'
import { Rnd } from 'react-rnd'

interface RndProps {
  content: {
    id: number
    width: number
    height: number
    x: number
    y: number
  }
  disableDragging: boolean
  enableResizing: boolean
  onResize?: (e, direction, ref, delta, position) => void
  style?: React.CSSProperties
  children: JSX.Element
  onDragStop?: (e, d) => void
  onResizeStop?: (e, direction, ref, delta, position) => void
}

const RND = (props: RndProps) => {
  const {
    content,
    onDragStop,
    onResize,
    onResizeStop,
    style,
    disableDragging,
    enableResizing,
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
      enableResizing={enableResizing}
    >
      {props.children}
    </Rnd>
  )
}

RND.defaultProps = {
  style: '',
  content: {
    width: '300px',
    height: '300px',
    x: 600,
    y: 600,
  },
  disableDragging: false,
  enableResizing: true,
  onDragStop: (e, d) => {},
  onResize: (e, direction, ref, delta, position) => {},
  onResizeStop: (e, direction, ref, delta, position) => {},
}

export default RND
