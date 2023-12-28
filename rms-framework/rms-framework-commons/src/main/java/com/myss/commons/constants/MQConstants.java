package com.myss.commons.constants;


public final class MQConstants {
    private MQConstants() {
        throw new IllegalStateException("MQConstants class");
    }
    /**
     * 队列名称
     */
    public static final String DIRECT_INSERT_QUEUE = "content.insert";
    /**
     * 交换器名称
     */
    public static final String DIRECT_EXCHANGE_NAME = "content-exchange";
    /**
     * 新增队列
     */
    public static final String DIRECT_INSERT_KEY = "course.insert";
    /**
     * 删除队列
     */
    public static final String DIRECT_DELETE_QUEUE = "content.delete";
    /**
     * 键
     */
    public static final String DIRECT_DELETE_KEY = "course.delete";
}
