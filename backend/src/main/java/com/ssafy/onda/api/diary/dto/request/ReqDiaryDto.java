package com.ssafy.onda.api.diary.dto.request;

import com.ssafy.onda.api.diary.dto.MemoListDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@ToString(of = { "diaryDate" })
@NoArgsConstructor
@Getter
public class ReqDiaryDto {

    @Pattern(regexp = "^[0-9]{4}+[-]+[0-9]{2}+[-]+[0-9]{2}$", message = "날짜를 확인해주세요.")
    private String diaryDate;

    @NotNull(message = "데이터를 확인해주세요.")
    private List<MemoListDto> memoList;

}
