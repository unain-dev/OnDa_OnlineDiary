import React, { useEffect, useState } from 'react'
import { Resizable } from 're-resizable'
import type { NextPage } from 'next'

interface Props {
  header?: any
  content?: any
  onUpdateButtonClick: any
  onDeleteButtonClick: any
  onApproveUpdateClick: any
  isEditable: boolean
}

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
  content,
  header,
  onUpdateButtonClick,
  onDeleteButtonClick,
  onApproveUpdateClick,
  isEditable,
}) => {

  return (
    <div>
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
    </div>
  )
}

export default MemoFrame
