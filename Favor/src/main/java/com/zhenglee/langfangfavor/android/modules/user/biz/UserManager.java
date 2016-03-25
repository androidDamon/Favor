package com.zhenglee.langfangfavor.android.modules.user.biz;

import android.content.Context;

import com.zhenglee.framework.business.AbstractBusinessObject;
import com.zhenglee.framework.business.BusinessContext;
import com.zhenglee.framework.persistence.DataAccessObject;
import com.zhenglee.langfangfavor.android.Favor;
import com.zhenglee.langfangfavor.android.modules.persistence.FavorPersistenceManager;
import com.zhenglee.langfangfavor.android.modules.user.dao.LocalUser;

/**
 * Created by zhenglee on 15/12/25.
 */
public class UserManager extends AbstractBusinessObject {

    private final DataAccessObject<LocalUser> luDao;
    /**
     * Create an instance with the specified {@link BusinessContext}
     *
     * @param context The business context
     */
    public UserManager(Context context) {
        super((BusinessContext) context.getApplicationContext());
        final Favor app = (Favor)context.getApplicationContext();
        final FavorPersistenceManager bpm = app.getPersistenceManager();
        this.luDao = bpm.getDataAccessObject(LocalUser.class);
    }
}
