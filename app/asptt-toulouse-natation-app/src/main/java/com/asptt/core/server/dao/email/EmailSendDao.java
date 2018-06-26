package com.asptt.core.server.dao.email;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.email.EmailSendEntity;

public class EmailSendDao extends DaoBase<EmailSendEntity> {

	@Override
	public Class<EmailSendEntity> getEntityClass() {
		return EmailSendEntity.class;
	}

	@Override
	public String getAlias() {
		return "emailsend";
	}

	@Override
	public Object getKey(EmailSendEntity pEntity) {
		return pEntity.getId();
	}

}
