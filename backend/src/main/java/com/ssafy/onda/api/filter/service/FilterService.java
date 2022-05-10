package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.global.common.auth.CustomUserDetails;

public interface FilterService {

    Object preview(CustomUserDetails details, int memoTypeSeq, String memoSeqList);

}
