package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeIfs;
import org.delivery.api.common.error.Errorcode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    
    /*
    * 상황	HTTP Status	Body	            유형
    * 정상	200	data만 있음	                성공
    * 실패	400/500	error만 있음	        일반 실패
    * 혼합	400 또는 200	data + error	혼합 케이스 < 이거에 대한 부분도 대응해야함(해당 프로젝트에는 제외됨)
    * */

    private Integer resultCode;

    private String resultMessage;

    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(Errorcode.OK.getErrorCode())
                .resultMessage(Errorcode.OK.getDescription())
                .resultDescription(Errorcode.OK.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(errorCodeIfs.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String msg){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(msg)
                .build();
    }

}
