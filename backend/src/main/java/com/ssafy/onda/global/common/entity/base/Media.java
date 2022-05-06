package com.ssafy.onda.global.common.entity.base;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@ToString(of = { "originName", "encodingName", "savedPath" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Media {

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String encodingName;

    @Column(nullable = false)
    private String savedPath;

    @Builder
    public Media(String originName, String encodingName, String savedPath) {
        this.originName = originName;
        this.encodingName = encodingName;
        this.savedPath = savedPath;
    }
}
