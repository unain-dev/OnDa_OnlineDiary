import { NodeNextRequest } from 'next/dist/server/base-http/node'
import React, { useEffect, useState } from 'react'
import MemoFrame from '../memoCommon/MemoFrame'
const textAreaStyle = {
  resize: 'none',
  width: '160px',
  height: '140px',
} as const
const headerAreaStyle = {
  width: '60px',
} as const
const MemoText = () => {
  const [header, setHeader] = useState('header')
  const [headerContent, setHeaderContent] = useState(<>testHeader</>)
  const [text, setText] = useState('')
  const [content, setContent] = useState(<>hello</>)
  const [textEditMode, setTextEditMode] = useState(false)

  useEffect(() => {}, [textEditMode])
  useEffect(() => {
    console.log(text)
    console.log(header)
  }, [[text, header]])
  const showInputTag = (type) => {
    if (type === 'CONTENT') {
      return (
        <textarea
          defaultValue={text}
          onChange={(e) => {
            setText(e.target.value)
          }}
          style={textAreaStyle}
        />
      )
    } else if (type === 'HEADER') {
      return (
        <input
          defaultValue={header}
          onChange={(e) => {
            setHeader(e.target.value)
          }}
          style={headerAreaStyle}
        />
      )
    }
  }
  const showTextTag = () => {
    return (
      <div>
        {text.split('\n').map((line) => {
          return (
            <span>
              {line}
              <br />
            </span>
          )
        })}
      </div>
    )
  }
  const showHeaderTag = () => {
    return <div>{header}</div>
  }
  const onDeleteButtonClick = () => {
    console.log('delete 구현')
  }
  const onUpdateButtonClick = () => {
    setTextEditMode(true)
    setContent(showInputTag('CONTENT'))
    setHeaderContent(showInputTag('HEADER'))
  }
  const onApproveUpdateClick = () => {
    setTextEditMode(false)
    setContent(showTextTag)
    setHeaderContent(showHeaderTag)
  }
  return (
    <MemoFrame
      width={200}
      height={200}
      content={content}
      header={headerContent}
      onDeleteButtonClick={onDeleteButtonClick}
      onUpdateButtonClick={onUpdateButtonClick}
      onApproveUpdateClick={onApproveUpdateClick}
      isEditable={textEditMode}
    ></MemoFrame>
  )
}

export default MemoText
