package com.ssafy.onda.global.common.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@ToString(of = { "originName", "encodedName", "savedPath" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FileInfo {

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String encodedName;

    @Column(nullable = false)
    private String savedPath;

    @Builder
    public FileInfo(String originName, String encodedName, String savedPath) {
        this.originName = originName;
        this.encodedName = encodedName;
        this.savedPath = savedPath;
    }
}
