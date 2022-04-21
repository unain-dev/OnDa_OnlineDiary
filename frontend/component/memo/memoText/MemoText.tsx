import { NodeNextRequest } from 'next/dist/server/base-http/node'
import { NextPage } from 'next/types'
import React, { useEffect, useState } from 'react'
import MemoFrame from '../memoCommon/MemoFrame'
interface Props {
  width: number,
  height: number,
  content: any,
  header: any,
}
const textAreaStyle = {
  resize: 'none',
  width: '160px',
  height: '140px',
} as const
const headerAreaStyle = {
  width: '60px',
} as const
const MemoText: NextPage<Props> = ({width, height, content, header}) => {
  const [headerText, setHeaderText] = useState(header)
  const [headerContent, setHeaderContent] = useState(<>{header}</>)
  const [text, setText] = useState(content)
  const [textContent, setTextContent] = useState(<>{content}</>)
  const [textEditMode, setTextEditMode] = useState(false)

  useEffect(() => {}, [textEditMode])
  useEffect(() => {
    console.log(text, headerText)
  }, [[text, headerText]])
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
          defaultValue={headerText}
          onChange={(e) => {
            setHeaderText(e.target.value)
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
    return <div>{headerText}</div>
  }
  const onDeleteButtonClick = () => {
    console.log('delete 구현')
  }
  const onUpdateButtonClick = () => {
    setTextEditMode(true)
    setTextContent(showInputTag('CONTENT'))
    setHeaderContent(showInputTag('HEADER'))
  }
  const onApproveUpdateClick = () => {
    setTextEditMode(false)
    setTextContent(showTextTag)
    setHeaderContent(showHeaderTag)
  }
  return (
    <MemoFrame
      content={textContent}
      header={headerContent}
      onDeleteButtonClick={onDeleteButtonClick}
      onUpdateButtonClick={onUpdateButtonClick}
      onApproveUpdateClick={onApproveUpdateClick}
      isEditable={textEditMode}
    ></MemoFrame>
  )
}

export default MemoText
