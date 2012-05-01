package org.nutz.token;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.repo.Base64;
import org.nutz.resource.Scans;

public class SafeSetup implements Setup {
	
	public void init(NutConfig config) {
		Dao dao = config.getIoc().get(Dao.class);
		for (Class<?> klass : Scans.me().scanPackage("org.nutz.safe.bean")) {
			if (klass.getAnnotation(Table.class) != null)
				dao.create(klass, false);
		}
		
		//初始化Enc
		//--------------------------------------------------------------
		
		//获取md5key
		SystemConfig md5key = dao.fetch(SystemConfig.class, "enc.md5key");
		if (md5key == null) {
			md5key = new SystemConfig();
			md5key.setName("enc.md5key");
			md5key.setData(R.sg(16).next());
		}
		
		//获取系统的公钥
		SystemConfig sysPubKey = dao.fetch(SystemConfig.class, "enc.sys.pubkey");
		SystemConfig sysPriKey = dao.fetch(SystemConfig.class, "enc.sys.prikey");
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		if (sysPubKey == null) {
			try {
				KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
				SecureRandom random = new SecureRandom();
				keygen.initialize(1024, random);
				KeyPair kp = keygen.generateKeyPair();
				publicKey = kp.getPublic();
				privateKey = kp.getPrivate();
				
				sysPubKey = new SystemConfig();
				sysPubKey.setName("enc.sys.pubkey");
				sysPubKey.setData(Base64.encodeToString(obj2bytes(publicKey), false));
				dao.insert(sysPubKey);
				
				sysPriKey = new SystemConfig();
				sysPriKey.setName("enc.sys.prikey");
				sysPriKey.setData(Base64.encodeToString(obj2bytes(privateKey), false));
				dao.insert(sysPriKey);
			} catch (Exception e) {
				throw Lang.wrapThrow(e);
			}
		} else {
			try {
				publicKey = (PublicKey) bytes2obj(sysPubKey.getData().getBytes());
				privateKey = (PrivateKey) bytes2obj(sysPriKey.getData().getBytes());
			} catch (Exception e) {
				throw Lang.wrapThrow(e);
			}
		}
		
		Enc.initSys(md5key.getData(), publicKey, privateKey);
	}
	
	public void destroy(NutConfig config) {
	}
	
	public static byte[] obj2bytes(Object obj) throws Exception {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bao);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		return bao.toByteArray();
	}
	
	public static Object bytes2obj(byte[] data) throws Exception {
		ByteArrayInputStream bai = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bai);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
}
