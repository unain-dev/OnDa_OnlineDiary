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
  console.log(info)
  const [file, setFile] = useState(null)
  const [previewImage, setPreviewImage] = useState(info)
  const [isEditable, setIsEditable] = useState(false)
  useEffect(()=>{
    console.log(info)
    console.log(info.name)
    // console.log(URL.createObjectURL(info))
    if(info.name !== null && info.name !== undefined) {
      console.log("in")
      // setFile(info)
      setPreviewImage(URL.createObjectURL(info))
    }
  },[info])
  const handleChange = (file) => {
    setFile(file)
    console.log(file)
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
    console.log(file)
    console.log(previewImage)
    if(Object.keys(previewImage).length===0){
      dispatch(      
        changeMemoState({
        ...memoInfo,
        isEditing: false,
      }),
      )
      setIsEditable(false)
      drag.enableDragging()
    }
    else if(file===null){
      dispatch(      
        changeMemoState({
        ...memoInfo,
        info: previewImage,
        isEditing: false,
      }),
      )
      setIsEditable(false)
      drag.enableDragging()
    }
    else{
      dispatch(
        changeMemoState({
          ...memoInfo,
          info: file[0],
          isEditing: false,
        }),
      )
      setIsEditable(false)
      drag.enableDragging()
    }

  }
  const onDeleteButtonClick = () => {
    onDeleteMemo(memoInfo.id)
  }
  useEffect(() => {
    console.log(file)
    if (file !== null) {
      setPreviewImage(URL.createObjectURL(file[0]))
    }
  }, [file])
  const [mouseState, setMouseState] = useState(false);
  
  const mouseOverEvent = () =>{
    setMouseState(true);
  }
  const mouseLeaveEvent = () =>{
    setMouseState(false);
  }
  return (
    <div className="App" onMouseOver={mouseOverEvent} onMouseLeave={mouseLeaveEvent}>
      {mouseState && <div className={styles.deleteButton} onClick={onDeleteButtonClick} >
        ❌
      </div>}
      {mouseState && !isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      {previewImage !== null && (
        <img className={styles.image} src={previewImage} style={{width: width, height: height}}  />
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
      {mouseState && isEditable && (
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
