package com.dhcc.business.de.dao.impl;

import com.dhcc.business.common.dao.BaseDaoImpl;
import com.dhcc.business.de.dao.MyUserDao;
import com.dhcc.business.de.api.entity.MyUserItem;
import org.springframework.stereotype.Repository;

@Repository("myUserDao")
public class MyUserDaoImpl extends BaseDaoImpl<MyUserItem> implements MyUserDao {
}
