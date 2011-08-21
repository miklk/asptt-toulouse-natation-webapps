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

import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.shared.userspace.admin.user.UpdateUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.UpdateUserResult;
import com.google.appengine.repackaged.com.google.common.io.MessageDigestAlgorithm;

public class UpdateUserActionHandler implements
		ActionHandler<UpdateUserAction, UpdateUserResult> {

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	
	public UpdateUserResult execute(UpdateUserAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Get user
		UserEntity lUser = userDao.get(pAction.getId());
		lUser.setEmailaddress(pAction.getEmailAddress());
		lUser.setValidated(pAction.isValidated());
		lUser.setProfiles(new HashSet<String>(pAction.getProfiles()));
		lUser.setSlots(pAction.getSlots());
		userDao.save(lUser);
		
		if(lUser.getPassword() == null || lUser.getPassword().isEmpty()) {
			
			try {
				Random lRandom = new Random(42788);
				MessageDigest lMessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.MD5.name());
				String lCode = Integer.toString(lRandom.nextInt(1000));
				System.out.println(lCode);
				String lEncryptedPassword = new String(lMessageDigest.digest(lCode.getBytes("UTF-8")));
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

	        String msgBody = "Vous pouvez maintenant accéder à votre espace utilisateur sur le site de l'ASPTT Toulouse Natation http://asptt-toulouse-natation.com/ utilisant le code suivant: " + lUser.getPassword();

	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(lUser.getEmailaddress()));
	            msg.setSubject("Votre compte web a été activé.");
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
		}
		
		//Get user data
		UserDataEntity lUserData = userDataDao.get(lUser.getUserData());
		lUserData.setLastName(pAction.getLastName());
		lUserData.setFirstName(pAction.getFirstName());
		lUserData.setBirthday(pAction.getBirthday());
		lUserData.setPhonenumber(pAction.getPhonenumber());
		lUserData.setAddressRoad(pAction.getAddressRoad());
		lUserData.setAddressCode(pAction.getAddressCode());
		lUserData.setAddressCity(pAction.getAddressCity());
		userDataDao.save(lUserData);
		return new UpdateUserResult();
	}

	public Class<UpdateUserAction> getActionType() {
		return UpdateUserAction.class;
	}

	public void rollback(UpdateUserAction pArg0, UpdateUserResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
