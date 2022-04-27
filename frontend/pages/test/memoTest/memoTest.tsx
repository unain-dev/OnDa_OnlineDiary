import React from 'react'
import 'react-quill/dist/quill.snow.css';
import dynamic from 'next/dynamic'
import MemoChecklist from '../../../component/memo/memoCheckList/MemoChecklist'
const QuillWrapper = dynamic(import('react-quill'), {
  ssr: false,
  loading: () => <p>Loading ...</p>,
})

const memoTest = () => {
  return (
    <div>
       <MemoChecklist />
    </div>
  )
}

export default memoTest
