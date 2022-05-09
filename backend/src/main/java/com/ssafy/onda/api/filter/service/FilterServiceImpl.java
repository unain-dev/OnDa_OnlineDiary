package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.filter.dto.request.ReqMonthFilterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FilterServiceImpl implements FilterService{

    
}
