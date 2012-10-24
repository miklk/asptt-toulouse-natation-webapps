package com.asptttoulousenatation.server.userspace.admin.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
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

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.server.util.Utils;
import com.asptttoulousenatation.shared.userspace.admin.user.CreateUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.CreateUserResult;

public class CreateUserActionHandler implements
		ActionHandler<CreateUserAction, CreateUserResult> {

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private SwimmerDao swimmerDao = new SwimmerDao();

	public CreateUserResult execute(CreateUserAction pAction,
			ExecutionContext pContext) throws DispatchException {
		UserDataEntity lUserData = new UserDataEntity();
		lUserData.setLastName(pAction.getLastName());
		lUserData.setFirstName(pAction.getFirstName());
		lUserData.setBirthday(pAction.getBirthday());
		lUserData.setPhonenumber(pAction.getPhonenumber());
		lUserData.setAddressRoad(pAction.getAddressRoad());
		lUserData.setAddressAdditional(pAction.getAddressAdditional());
		lUserData.setAddressCode(pAction.getAddressCode());
		lUserData.setAddressCity(pAction.getAddressCity());
		UserDataEntity lUserDataCreated = userDataDao.save(lUserData);

		UserEntity lUser = new UserEntity();
		lUser.setEmailaddress(pAction.getEmailAddress());
		lUser.setValidated(pAction.isValidated());
		lUser.setProfiles(new HashSet<String>(pAction.getProfiles()));
		lUser.setSlots(pAction.getSlots());
		lUser.setUserData(lUserDataCreated.getId());

		if (pAction.isValidated()) {

			try {
				Random lRandom = new Random(42788);
				MessageDigest lMessageDigest = Utils.getMD5();
				String lCode = Integer.toString(lRandom.nextInt(1000));
				System.out.println(lCode);
				lMessageDigest.update(lCode.getBytes());
				String lEncryptedPassword = new String(lMessageDigest.digest());
				lUser.setPassword(lEncryptedPassword);
				System.out.println(lUser.getPassword());

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				String msgBody = "Vous pouvez maintenant accéder à votre espace utilisateur sur le site de l'ASPTT Toulouse Natation http://asptt-toulouse-natation.com/ utilisant le code suivant: "
						+ lCode;

				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						lUser.getEmailaddress()));
				msg.setSubject("Votre compte web a été activé.", "UTF-8");
				msg.setText(msgBody, "UTF-8");
				Transport.send(msg);

			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		UserEntity userDB = userDao.save(lUser);

		// Create swimmer
		if (pAction.isSwimmerStat()) {
			SwimmerEntity lSwimmerEntity = new SwimmerEntity();
			lSwimmerEntity.setStat(pAction.isSwimmerStat());
			lSwimmerEntity.setUser(userDB.getId());
			swimmerDao.save(lSwimmerEntity);
		}

		return new CreateUserResult();
	}

	public Class<CreateUserAction> getActionType() {
		return CreateUserAction.class;
	}

	public void rollback(CreateUserAction pArg0, CreateUserResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
