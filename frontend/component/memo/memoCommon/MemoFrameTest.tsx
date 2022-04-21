import React, { useEffect, useState } from 'react'
import { Resizable } from 're-resizable'
import type { NextPage } from 'next'
import { red } from '@mui/material/colors'

interface Props {
  width: number
  height: number
  header?: string
  content?: any
}
const style = {
  position: 'relative',
  border: 'solid 1px #ddd',
  background: '#f0f0f0',
  borderRadius: '15px',
  overflow: 'hidden',
} as const
const headerStyle = {
  position: 'absolute',
  top: '0%',
  left: '0%',
  margin: '10px',
} as const
const contentStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
} as const
const deleteButton = {
  position: 'absolute',
  top: '0%',
  right: '0%',
  margin: '10px',
  cursor: 'pointer',
} as const

const test = {
  background: '#8bbaff',
} as const

const MemoFrameTest: NextPage<Props> = ({ width, height, content, header }) => {
  // const [size, setSize] = useState({
  //   width: width,
  //   height: height,
  // })
  // useEffect(() => {
  //   console.log(size)
  // }, [size])
  const onDeleteButtonClick = () => {
    console.log('delete 구현')
  }
  return (
    <div style={test}>
      <div style={deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>
      <div style={headerStyle}>{header}</div>
      <div style={contentStyle}> {content}</div>
    </div>
  )
}

export default MemoFrameTest