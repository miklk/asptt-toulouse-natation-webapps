package com.asptttoulousenatation.core.server.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.shared.user.ChangePasswordAction;
import com.asptttoulousenatation.core.shared.user.ChangePasswordResult;
import com.asptttoulousenatation.server.util.Utils;

public class ChangePasswordActionHandler implements
		ActionHandler<ChangePasswordAction, ChangePasswordResult> {

	private UserDao dao = new UserDao();
	
	public ChangePasswordResult execute(ChangePasswordAction pAction,
			ExecutionContext pContext) throws DispatchException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, pAction.getUserName(), Operator.EQUAL));
		List<UserEntity> entities = dao.find(criteria);
		ChangePasswordResult result;
		final UserEntity lUser;
		if(CollectionUtils.isNotEmpty(entities)) {
			lUser = entities.get(0);
			try {
				Random lRandom = new Random(42788);
				MessageDigest lMessageDigest = Utils.getMD5();
				
				//Compare password
				lMessageDigest.update(pAction.getOldPassword().getBytes());
				String oldPassword = new String(lMessageDigest.digest());
				if(StringUtils.equalsIgnoreCase(oldPassword, lUser.getPassword())) {
					lMessageDigest = Utils.getMD5();
					lMessageDigest.update(pAction.getNewPassword().getBytes());
					String newPassword = new String(lMessageDigest.digest());
					lUser.setPassword(new String(newPassword));

					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);

					String msgBody = "Votre nouveau mot de passe '"+ pAction.getNewPassword()+"' est maintenant activé.<br />Vous pouvez maintenant accéder à votre espace utilisateur sur le site de l'ASPTT Toulouse Natation http://asptt-toulouse-natation.com/";

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							"webmaster@asptt-toulouse-natation.com",
							"ASPTT Toulouse Natation"));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
							lUser.getEmailaddress()));
					msg.setSubject("Changement de mot de passe.", "UTF-8");
					msg.setText(msgBody, "UTF-8");
					Transport.send(msg);
					result = new ChangePasswordResult(true);
				} else {
					result = new ChangePasswordResult(false);
					result.setNotSame(true);
				}
				

			} catch (AddressException e) {
				// ...
				result = new ChangePasswordResult(false);
			} catch (MessagingException e) {
				result = new ChangePasswordResult(false);
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ChangePasswordResult(false);
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				result = new ChangePasswordResult(false);
			}
		} else {
			lUser = null;
			result = new ChangePasswordResult(false);
		}
		return result;
	}

	public Class<ChangePasswordAction> getActionType() {
		return ChangePasswordAction.class;
	}

	public void rollback(ChangePasswordAction pAction,
			ChangePasswordResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}