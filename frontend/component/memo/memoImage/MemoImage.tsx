import { useEffect, useState } from 'react'
import { FileUploader } from 'react-drag-drop-files'
import { useDispatch } from 'react-redux'
import { changeMemoState } from 'core/store/modules/diary'
import styles from '../../../styles/scss/Memo.module.scss'
const fileTypes = ['JPEG', 'PNG', 'GIF']
interface Props {
  width: number
  height: number
  content: any
  header: any
  drag: any
  memoInfo: any
  onDeleteMemo: any
}
export default function MemoImage({ memoInfo, drag, onDeleteMemo }) {
  const dispatch = useDispatch()
  const { width, height, info } = memoInfo
  const [file, setFile] = useState(null)
  const [previewImage, setPreviewImage] = useState(info)
  const [isEditable, setIsEditable] = useState(false)
  const handleChange = (file) => {
    setFile(file)
  }
  const onUpdateButtonClick = () => {
    setIsEditable(true)
    drag.disableDragging()
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: true,
      }),
    )
  }
  const onApproveUpdateClick = () => {
    setIsEditable(false)
    drag.enableDragging()
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: false,
      }),
    )
  }
  const onDeleteButtonClick = () => {
    onDeleteMemo(memoInfo.id)
  }
  useEffect(() => {
    if (file !== null) {
      setPreviewImage(URL.createObjectURL(file[0]))
    }
  }, [file])
  return (
    <div className="App">
      <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>
      {!isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      {previewImage !== null && (
        <img src={previewImage} className={styles.image} />
      )}
      {isEditable && (
        <div className={styles.fileUploader}>
          <FileUploader
            multiple={true}
            handleChange={handleChange}
            name="file"
            types={fileTypes}
          />
        </div>
      )}
      {isEditable && (
        <div
          className={styles.approveUpdateButton}
          onClick={onApproveUpdateClick}
        >
          ✔️
        </div>
      )}
    </div>
  )
}
