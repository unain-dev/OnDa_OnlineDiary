import React, { useEffect, useRef, useState } from 'react'
import FullCalendar from '@fullcalendar/react'
import interactionPlugin from '@fullcalendar/interaction'
import timeGridPlugin from '@fullcalendar/timegrid'
import dayGridPlugin from '@fullcalendar/daygrid'
import styles from '../../styles/scss/Collection.module.scss'
import CollectionPannel from 'component/collection/collectionPannel'
import { useRouter } from 'next/router'
import { AppDispatch } from 'core/store'
import { useDispatch, useSelector } from 'react-redux'
import {
  getCollectionMemoListAction,
  getCollectionMemoAction,
} from 'core/store/actions/collection'
import cookies from 'next-cookies'
import { getIsMember } from 'core/api/auth'
import DatePicker from "react-datepicker";
// import "@fullcalendar/core/main.css";
import "@fullcalendar/daygrid/main.css";
import "@fullcalendar/timegrid/main.css";
import MonthPickerModule from 'component/collection/MonthPickerModule/MonthPickerModule'
const month = ({ token }) => {
  const calenderRef = useRef();
  const router = useRouter()
  const appDispatch: AppDispatch = useDispatch()
  const [collectionPannelIsOpen, setCollectionPannelIsOpen] = useState(false)
  const [searchInput, setSearchInput] = useState(null)
  const [extendedProps, setExtendedProps] = useState({})
  const [selectType, setSelectType] = useState(0)
  const previewInfo = useSelector(
    ({ collection }) => collection.collectionMemoListInfo,
  )
  console.log(previewInfo)

  const selectChanged = (e) => {
    console.log(typeof Number(e.target.value))
    setSelectType(Number(e.target.value))
  }
  const getBriefInfo = () => {
    let params = {
      type: selectType,
      token: token,
      keyword: searchInput,
    }
    // if(searchInput !== undefined && searchInput !==null) params['keyword'] = searchInput;
    console.log(params)
    appDispatch(getCollectionMemoListAction(params))
  }
  useEffect(() => {
    getBriefInfo()
  }, [selectType])

  const onCalenderEventClick = (e) => {
    setCollectionPannelIsOpen(false)
    console.log(e.event._def)
    console.log(typeof e.event._def.extendedProps)
    const params = {
      memoTypeSeq: e.event._def.extendedProps.memoTypeSeq,
      memoSeqList: e.event._def.extendedProps.memoSeqList.toString(),
      token: token,
    }
    console.log(params)
    appDispatch(getCollectionMemoAction(params)).then(() => {
      setExtendedProps(() => e.event._def.extendedProps)
      setCollectionPannelIsOpen(true)
    })
  }
  const searchInputChange = (e) => {
    console.log(e.target.value)
    setSearchInput(e.target.value)
  }
  const onEnterInput = (e) => {
    if (e.key === 'Enter') searchByKeyword()
  }
  const searchByKeyword = () => {
    let params = {
      type: selectType,
      token: token,
      keyword: searchInput,
    }
    appDispatch(getCollectionMemoListAction(params))
  }
  const [doubleClickState, setDoubleClickState] = useState({
    state: false,
    date: '',
  })
  let doubleClickThrottle
  const onDateClick = (date) => {
    if (doubleClickState.date !== date) {
      setDoubleClickState({ state: true, date: date })
      doubleClickThrottle = setTimeout(() => {
        setDoubleClickState({ state: false, date: date })
      }, 200)
      return
    }
    if (doubleClickState.state) {
      router.push(`/diary/${date}`)
    } else {
      setDoubleClickState({ state: true, date: date })
      doubleClickThrottle = setTimeout(() => {
        setDoubleClickState({ state: false, date: date })
      }, 200)
    }
  }
  const renderObject = () => {
    return (
      <CollectionPannel
        token={token}
        onCloseBtn={() => {
          setCollectionPannelIsOpen(false)
        }}
        info={{ ...extendedProps }}
      />
    )
  }
  //date picker
  const gotoDate = (date) => {
    let calendarApi = calenderRef.current.getApi();
    calendarApi.gotoDate(date); // call a method on the Calendar object
  };
  const [startDate, setStartDate] = useState(new Date());
  return (
    <div>
      <div className={styles.month}>
        <input
          className={styles.searchInput}
          onChange={(e) => searchInputChange(e)}
          onKeyDown={(e) => onEnterInput(e)}
        />
        <button className={styles.searchBtn} onClick={searchByKeyword}>
          검색
        </button>
      </div>
      <div className={styles.dropdownBar} style={{ width: '70%' }}>
        <select className={styles.selectBox} onChange={(e) => selectChanged(e)}>
          <option value={0}>전체보기</option>
          <option value={1}>텍스트</option>
          <option value={2}>가계부</option>
          <option value={3}>체크리스트</option>
        </select>
        <div className={styles.selectMonthBtn}>
          <MonthPickerModule gotoDate={gotoDate}/>
          {/* <button onClick={gotoDate}>test button</button> */}
        </div>
      </div>
      {collectionPannelIsOpen && renderObject()}
      <div className={styles.calender} style={{ width: '75%' }}>
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          editable
          selectable
          dateClick={(e) => onDateClick(e.dateStr)}
          events={previewInfo}
          eventClick={(e) => {
            onCalenderEventClick(e)
          }}
          ref={calenderRef}
          headerToolbar={{
            start: 'title', // will normally be on the left. if RTL, will be on the right
            // center: 'today',
            end: 'today prev next', // will normally be on the right. if RTL, will be on the left
          }}
        />
      </div>
    </div>
  )
}
export async function getServerSideProps(context) {
  const t = cookies(context).member
  const token = t === undefined ? null : t
  const isMember =
    t === undefined ? false : await getIsMember(cookies(context).member)

  return {
    props: {
      // diaryDate: context.params.diaryDate,
      isMember: isMember,
      token: token,
    },
  }
}
export default month
