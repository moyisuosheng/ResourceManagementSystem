package com.myss.commons.constants;

/**
 * 默认常量
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants Class Illegal");
    }

    /**
     * 有效载荷
     */
    public static final String PAYLOAD = "payload";

    /**
     * 正则表达式
     *
     * @author zhurongxu
     * @version 1.0.0
     * @date 2023/12/28
     */
    public static final class Regexps {
        private Regexps() {
            throw new IllegalStateException("Constants Regexps Class Illegal");
        }

        /**
         * 电话
         */
        public static final String PHONE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    }


    public static final class IndexNames {
        private IndexNames() {
            throw new IllegalStateException("Constants IndexNames Class Illegal");
        }

        /**
         * 文件索引库
         */
        public static final String FILE = "file";

    }

    public static final class Analyzer {
        private Analyzer() {
            throw new IllegalStateException("Constants Analyzer Class Illegal");
        }

        /**
         * 将文本做最粗粒度的拆分
         */
        public static final String IK_SMART = "ik_smart";

        /**
         * 将文本做最细粒度的拆分
         */
        public static final String IK_MAX_WORD = "ik_max_word";

    }

    public static final class User {
        private User() {
            throw new IllegalStateException("Constants User Class Illegal");
        }

        /**
         * 用户token的key
         */
        public static final String USER_ID = "USER_ID:";
    }
}
