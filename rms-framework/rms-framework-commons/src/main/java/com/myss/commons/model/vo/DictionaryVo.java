package com.myss.commons.model.vo;

import lombok.Data;

/**
 * 词典 VO
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/27
 */
@Data
public class DictionaryVo {


    /**
     * 编号
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 项目值
     */
    private String itemValues;

}