import React, { useRef  } from 'react';
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import dayGridPlugin from "@fullcalendar/daygrid";

const month = () => {
    const events = [{ title: "today's event", date: new Date() }];
    
    const onCalenderEventClick=(e)=>{
        console.log(e.event.t);
    }

    return (
        <div>
            <FullCalendar
            plugins={[dayGridPlugin, interactionPlugin]}
            editable
            selectable
            events={events}
            eventClick={(e)=>{onCalenderEventClick(e)}}
            />
        </div>
    );
};

export default month;