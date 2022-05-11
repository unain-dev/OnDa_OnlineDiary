import React, { useRef  } from 'react';
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import dayGridPlugin from "@fullcalendar/daygrid";
import styles from '../../styles/scss/Collection.module.scss'

const month = () => {
    const events = [{ title: "텍스트 +4", date: '2022-05-09' },{ title: "가계부 +4", date: '2022-05-09' },{ title: "체크리스트 +4", date: '2022-05-09' }];
    const onCalenderEventClick=(e)=>{
        console.log(e.event.t);
    }

    return (
        <div>
            <div className={styles.month}>
                <input className={styles.searchInput}/>
                <button>검색</button>
            </div>
            <div className={styles.dropdownBar} style={{width: "70%"}}>
                <select>
                    <option>텍스트</option>
                    <option>가계부</option>
                    <option>체크리스트</option>
                    <option>이미지</option>
                    <option>스티커</option>
                </select>
            </div>
            
            <div className={styles.calender} style={{width: "70%" }}>
                <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                editable
                selectable
                events={events}
                eventClick={(e)=>{onCalenderEventClick(e)}}
                />
            </div>
        </div>
    );
};

export default month;
