import { NextPage } from 'next/types'
import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import MemoFrame from './MemoText.view'
import dynamic from 'next/dynamic'
import styles from '../../../styles/scss/Memo.module.scss'
import 'react-quill/dist/quill.snow.css'
import { changeText, changeMemoState } from '../../../core/store/modules/diary'

interface Props {
  memoInfo: any
  // width: number
  // height: number
  // content: any
  // header: any
  drag: any
  onDeleteMemo: any
}
const QuillWrapper = dynamic(() => import('react-quill'), {
  ssr: false,
  loading: () => <p>Loading ...</p>,
})

const MemoText: NextPage<Props> = ({ memoInfo, drag, onDeleteMemo }) => {
  const dispatch = useDispatch()

  // memoInfo의 변수들을 나눠서 설정(최대한 기존 코드의 네이밍을 건드리지 않기 위해서)
  const { width, height, info } = memoInfo
  const { content, header } = info

  const [headerText, setHeaderText] = useState(header)
  const [headerContent, setHeaderContent] = useState(header)
  const [text, setText] = useState(content)
  const [textContent, setTextContent] = useState(content)
  const [textEditMode, setTextEditMode] = useState(false)

  useEffect(() => {}, [textEditMode])
  useEffect(() => {}, [text, headerText])
  useEffect(() => {
    if (textEditMode) {
      setTextContent(showInputTag('CONTENT'))
      setHeaderContent(showInputTag('HEADER'))
    } else {
      // setTextContent(showTextTag)
      setTextContent(text)
      setHeaderContent(showHeaderTag)
    }
  })
  const showInputTag = (type) => {
    if (type === 'CONTENT') {
      return (
        <QuillWrapper
          theme="snow"
          value={text}
          onChange={(event) => setText(event)}
          style={{ width: width - 40, height: height - 100 }}
        />
      )
    } else if (type === 'HEADER') {
      return (
        <input
          defaultValue={headerText}
          onChange={(e) => {
            setHeaderText(e.target.value)
          }}
          className={styles.headerAreaStyle}
          style={{ width: width - 80 }}
        />
      )
    }
  }
  const showHeaderTag = () => {
    return <div>{headerText}</div>
  }
  const onDeleteButtonClick = () => {
    onDeleteMemo(memoInfo.id)
  }
  const onUpdateButtonClick = () => {
    setTextEditMode(true)
    setTextContent(showInputTag('CONTENT'))
    setHeaderContent(showInputTag('HEADER'))
    drag.disableDragging()
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: true,
      }),
    )
  }
  const onApproveUpdateClick = () => {
    setTextEditMode(false)
    // setTextContent(showTextTag)
    setTextContent(text)
    setHeaderContent(showHeaderTag)
    console.log(drag)
    drag.enableDragging()
    dispatch(
      changeText({
        ...memoInfo,
        info: {
          header: headerText,
          content: text,
        },
      }),
    )
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: false,
      }),
    )
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
