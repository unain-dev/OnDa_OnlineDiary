import React, { useRef, useState  } from 'react';
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import dayGridPlugin from "@fullcalendar/daygrid";
import styles from '../../styles/scss/Collection.module.scss'
import CollectionPannel from 'component/collection/collectionPannel';
import { useRouter } from 'next/router'
const month = () => {
    const router = useRouter();
    const [collectionPannelIsOpen, setCollectionPannelIsOpen] = useState(false);
    const [searchInput, setSearchInput] = useState();
    const [extendedProps, setExtendedProps] = useState({});
    const events = [
        { title: "텍스트 +4", date: '2022-05-09', dateProp: '2022-05-09', memoTypeSeq : 1, memoSeqList: ['3','4'] },
        { title: "가계부 +1", date: '2022-05-09', dateProp: '2022-05-09', memoTypeSeq : 2, memoSeqList: ['2'] },
        { title: "체크리스트 +1", date: '2022-05-09', dateProp: '2022-05-09', memoTypeSeq : 3, memoSeqList: ['2'] },
        { title: "텍스트 +4", date: '2022-05-10', dateProp: '2022-05-10', memoTypeSeq : 1, memoSeqList: ['3','4'] },
        { title: "가계부 +1", date: '2022-05-11', dateProp: '2022-05-11', memoTypeSeq : 2, memoSeqList: ['2'] },
        { title: "체크리스트 +1", date: '2022-05-12', dateProp: '2022-05-12', memoTypeSeq : 3, memoSeqList: ['2'] },
    ];
    const onCalenderEventClick=(e)=>{
        console.log(e.event._def);
        console.log(typeof e.event._def.extendedProps);
        setExtendedProps(()=>e.event._def.extendedProps);
        setCollectionPannelIsOpen(true);
    }
    const searchInputChange = (e) => {
        console.log(e.target.value);
        setSearchInput(e.target.value);
    }
    const onEnterInput = (e) => {
        if(e.key === 'Enter')   searchByKeyword();
    }
    const searchByKeyword = () => {
        console.log(searchInput);
    }
    const [doubleClickState, setDoubleClickState] = useState({state: false, date:''});
    let doubleClickThrottle;
    const onDateClick = (date) =>{
        if(doubleClickState.date !== date){
            setDoubleClickState({state: true, date: date});
            doubleClickThrottle = setTimeout(()=>{
                setDoubleClickState({state: false, date: date});
            }, 200)
            return;
        }
        if(doubleClickState.state){
            router.push(`/diary/${date}`)
        }
        else{
            setDoubleClickState({state: true, date: date});
            doubleClickThrottle = setTimeout(()=>{
                setDoubleClickState({state: false, date: date});
            }, 200)
        }
    }
    return (
        <div>
            <div className={styles.month}>
                <input className={styles.searchInput} onChange={(e)=>searchInputChange(e)} onKeyDown={(e)=>onEnterInput(e)}/>
                <button className={styles.searchBtn} onClick={searchByKeyword}>검색</button>
            </div>
            <div className={styles.dropdownBar} style={{width: "70%"}}>
                <select className={styles.selectBox}>
                    <option>전체보기</option>
                    <option>텍스트</option>
                    <option>가계부</option>
                    <option>체크리스트</option>
                </select>
            </div>
            {collectionPannelIsOpen && (
                <CollectionPannel
                onCloseBtn={() => {
                    setCollectionPannelIsOpen(false)
                }}
                info={extendedProps}
                />
            )}
            <div className={styles.calender} style={{width: "75%" }}>
                <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                editable
                selectable
                dateClick={(e)=>onDateClick(e.dateStr)}
                events={events}
                eventClick={(e)=>{onCalenderEventClick(e)}}
                />
            </div>
        </div>
    );
};

export default month;
