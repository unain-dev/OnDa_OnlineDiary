package com.ssafy.onda.global.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 컨트롤러(controller)가 아닌곳에서, 서버 응답값(바디) 직접 변경 및 전달 하기위한 유틸 정의.
 */
@Slf4j
public class ResponseBodyWriteUtil {

    public static void sendApiResponse(HttpServletResponse response, BaseResponseDto apiResponse) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(new ObjectMapper().writeValueAsString(apiResponse).getBytes());
    }

    public static void sendError(HttpServletRequest request, HttpServletResponse response, Exception ex, HttpStatus httpStatus) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        log.error("[ Error TimeStamp ] {}", Calendar.getInstance().getTime());

        String message = ex.getMessage();
        message = message == null ? "" : message;
        log.error("[ Error Message ] {}", message);

        String errorCode = ex.getClass().getSimpleName();
        if (ex.getClass().equals(CustomException.class)) {
            errorCode = ((CustomException) ex).getErrorStatus().getMsg();
        } else {
            log.error("[ Error Path ]  {}", request.getRequestURI());
        }

        Map<String, Object> data = ImmutableMap.of(
                "status", httpStatus.value(),
                "error", errorCode
        );
        PrintWriter pw = response.getWriter();
        pw.print(new ObjectMapper().writeValueAsString(data));
        pw.flush();
    }

    public static void sendError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        sendError(request, response, ex, HttpStatus.UNAUTHORIZED);
    }
}
