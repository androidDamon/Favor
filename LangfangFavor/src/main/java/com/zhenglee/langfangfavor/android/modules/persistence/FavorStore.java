package com.zhenglee.langfangfavor.android.modules.persistence;

import android.provider.BaseColumns;

/**
 * Created by zhenglee on 15/12/25.
 */
public final class FavorStore {

    private FavorStore(){
    };

    public static final class Users {

        public static final String CONTENT_CATEGORY = "user";

        public static final int GENDER_MALE = 1;

        public static final int GENDER_FEMAIL = 0;

        public static final int GENDER_UNKNOWN = -1;

        public static interface UserColumns extends BaseColumns{

            public static final String USERNAME = "username";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String NICKNAME = "nickname";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String PHONE = "phone";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String EMAIL = "email";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String SLOGAN = "slogan";

            /**
             * <p>
             * Type: INTEGER
             * </p>
             */
            public static final String SEX = "sex";

            /**
             * <p>
             * Type: INTEGER
             * </p>
             */
            public static final String HEIGHT = "height";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String AVATAR = "avatar";

            /**
             * <p>
             * Type: LONG
             * </p>
             */
            public static final String CREATED_TIME = "created_time";

            /**
             * <p>
             * Type: LONG
             * </p>
             */
            public static final String UPDATED_TIME = "updated_time";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String AREA = "area";

            /**
             * <p>
             * Type: TEXT
             * </p>
             */
            public static final String COMMUNITY = "community";

            /**
             * <p>
             * Type: LONG
             * </p>
             */

            public static final String LATEST_LOGIN_TIME = "latest_login_time";
        }
    }
}
