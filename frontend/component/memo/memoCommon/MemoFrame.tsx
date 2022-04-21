import React, { useEffect, useState } from 'react'
import { Resizable } from 're-resizable'
import type { NextPage } from 'next'

interface Props {
  width: number
  height: number
  header?: any
  content?: any
  onUpdateButtonClick: any
  onDeleteButtonClick: any
  onApproveUpdateClick: any
  isEditable: boolean
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
  margin: '20px',
  top: '20px',
  left: '0%',
} as const
const deleteButtonStyle = {
  position: 'absolute',
  top: '0%',
  right: '0px',
  margin: '10px',
  cursor: 'pointer',
} as const
const updateButtonStyle = {
  position: 'absolute',
  top: '0%',
  right: '30px',
  margin: '10px',
  cursor: 'pointer',
} as const
const approveUpdateButtonStyle = {
  position: 'absolute',
  bottom: '0%',
  right: '0px',
  margin: '10px',
  cursor: 'pointer',
} as const
const MemoFrame: NextPage<Props> = ({
  width,
  height,
  content,
  header,
  onUpdateButtonClick,
  onDeleteButtonClick,
  onApproveUpdateClick,
  isEditable,
}) => {
  const [size, setSize] = useState({
    width: width,
    height: height,
  })
  useEffect(() => {
    console.log(size)
  }, [size])

  return (
    <Resizable
      style={style}
      size={{
        width: size.width,
        height: size.height,
      }}
      onResizeStop={(e, direction, ref, d) => {
        setSize({ width: size.width + d.width, height: size.height + d.height })
      }}
    >
      <div style={deleteButtonStyle} onClick={onDeleteButtonClick}>
        ❌
      </div>
      {!isEditable && (
        <div style={updateButtonStyle} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      <div style={headerStyle}>{header}</div>
      <div style={contentStyle}> {content}</div>
      {isEditable && (
        <div style={approveUpdateButtonStyle} onClick={onApproveUpdateClick}>
          ✔️
        </div>
      )}
    </Resizable>
  )
}

export default MemoFrame
