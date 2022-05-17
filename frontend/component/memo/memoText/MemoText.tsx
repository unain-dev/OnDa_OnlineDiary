import { NextPage } from 'next/types'
import React, { useEffect, useState, useMemo } from 'react'
import { useDispatch } from 'react-redux'
import MemoFrame from './MemoText.view'
import dynamic from 'next/dynamic'
import styles from '../../../styles/scss/Memo.module.scss'
import 'react-quill/dist/quill.bubble.css'
import { changeMemoState } from '../../../core/store/modules/diary'



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
  console.log(memoInfo)
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
  useEffect(()=>{
    console.log(text)
    setHeaderContent(header);
    setHeaderText(header)
    setTextContent(content);
    setText(content);
  },[content, header])
  useEffect(() => {
    if (textEditMode) {
      setTextContent(showInputTag('CONTENT'))
      setHeaderContent(showInputTag('HEADER'))
    } else {
      // setTextContent(showTextTag)
      setTextContent(text)
      setHeaderContent(showHeaderTag)
    }
  },[])
  const toolbarOptions = useMemo(()=> ({
    toolbar: {
      container: [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],
      
        [{ 'header': 1 }, { 'header': 2 }, {'header': 3}],               // custom button values
        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
      
        [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
        [{ 'font': [] }],
      ]
    }
  }),[]) ;
  const showInputTag = (type) => {
    if (type === 'CONTENT') {
      return (
        <div className='quill' style={{ width: width, height: height - 50, marginTop: '20px' }}>
          <QuillWrapper
            theme="bubble"
            value={text}
            onChange={(event) => setText(event)}
            modules={toolbarOptions}
            placeholder="내용을 입력해주세요"
          />
        </div>
      )
    } else if (type === 'HEADER') {
      return (
        <input
          defaultValue={headerText}
          onChange={(e) => {
            setHeaderText(e.target.value)
          }}
          className={styles.headerAreaStyle}
          style={{ width: width - 45, background: 'transparent', border: '0px solid'}}
          placeholder="제목을 입력해주세요"
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
      changeMemoState({
        ...memoInfo,
        info: {
          header: headerText,
          content: text,
        },
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
      memoInfo={memoInfo}
    ></MemoFrame>
  )
}

export default MemoText
