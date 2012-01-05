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

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.shared.user.PasswordForgetAction;
import com.asptttoulousenatation.core.shared.user.PasswordForgetResult;
import com.asptttoulousenatation.server.util.Utils;

public class PasswordForgetActionHandler implements
		ActionHandler<PasswordForgetAction, PasswordForgetResult> {

	private UserDao userDao = new UserDao();

	public PasswordForgetResult execute(PasswordForgetAction pAction,
			ExecutionContext pContext) throws DispatchException {
		final PasswordForgetResult lResult = new PasswordForgetResult();
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<String> lCriterion = new CriterionDao<String>(
				UserEntityFields.EMAILADDRESS, pAction.getEmailAddress(),
				Operator.EQUAL);
		lCriteria.add(lCriterion);
		List<UserEntity> lUsers = userDao.find(lCriteria);
		if (!lUsers.isEmpty()) {
			UserEntity lUser = userDao.find(lCriteria).get(0);

			try {
				Random lRandom = new Random(42788);
				MessageDigest lMessageDigest = Utils.getMD5();
				String lCode = Integer.toString(lRandom.nextInt(1000));
				System.out.println(lCode);
				String lEncryptedPassword = new String(
						lMessageDigest.digest(lCode.getBytes("UTF-8")));
				lUser.setPassword(lEncryptedPassword);
				System.out.println(lUser.getPassword());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String msgBody = "Vous pouvez maintenant accéder à votre espace utilisateur sur le site de l'ASPTT Toulouse Natation http://asptt-toulouse-natation.com/ utilisant le code suivant: "
					+ lUser.getPassword();

			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						lUser.getEmailaddress()));
				msg.setSubject("Votre mot de passe a été initialisé");
				msg.setText(msgBody);
				Transport.send(msg);

			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userDao.save(lUser);
			lResult.setSended(true);
		} else {
			lResult.setSended(false);
		}
		return lResult;
	}

	public Class<PasswordForgetAction> getActionType() {
		return PasswordForgetAction.class;
	}

	public void rollback(PasswordForgetAction pAction,
			PasswordForgetResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}