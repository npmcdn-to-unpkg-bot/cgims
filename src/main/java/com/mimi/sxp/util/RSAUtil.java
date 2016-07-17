package com.mimi.sxp.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
public class RSAUtil {
//	private static final Logger LOG = LoggerFactory.getLogger(RSAUtil.class);

	private static String RSAKeyStore = "C:/RSAKey.txt";
	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */

	private static List<KeyPair> pairs = new ArrayList<KeyPair>();

	public static KeyPair generateKeyPair() throws Exception {
		// try {
		// LOG.info("开始生产密匙");
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
				new BouncyCastleProvider());
		final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
		keyPairGen.initialize(KEY_SIZE, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		if (RSAUtil.pairs.isEmpty()) {
			RSAUtil.pairs.add(keyPair);
		} else {
			if (RSAUtil.pairs.size() > 1) {
				RSAUtil.pairs.set(1, RSAUtil.pairs.get(0));
			} else {
				RSAUtil.pairs.add(RSAUtil.pairs.get(0));
			}
			RSAUtil.pairs.set(0, keyPair);
		}
		// System.out.println(keyPair.getPrivate());
		// System.out.println(keyPair.getPublic());

		// saveKeyPair(keyPair);
		// LOG.info("生产密匙完成");
		return keyPair;
		// } catch (Exception e) {
		// LOG.info("生产密匙出错");
		// throw new Exception(e.getMessage());
		// }
	}

	public static KeyPair getKeyPair() throws Exception {
		// FileInputStream fis = new FileInputStream(RSAKeyStore);
		// ObjectInputStream oos = new ObjectInputStream(fis);
		// KeyPair kp = (KeyPair) oos.readObject();
		// oos.close();
		// fis.close();
		// return kp;
		return RSAUtil.pairs.get(0);
	}

	public static void saveKeyPair(KeyPair kp) throws Exception {

		FileOutputStream fos = new FileOutputStream(RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		// try {
		keyFac = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		// } catch (NoSuchAlgorithmException ex) {
		// throw new Exception(ex.getMessage());
		// }

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		// try {
		return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		// } catch (InvalidKeySpecException ex) {
		// throw new Exception(ex.getMessage());
		// }
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		// try {
		keyFac = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		// } catch (NoSuchAlgorithmException ex) {
		// throw new Exception(ex.getMessage());
		// }

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		// try {
		return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		// } catch (InvalidKeySpecException ex) {
		// throw new Exception(ex.getMessage());
		// }
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		// try {
		Cipher cipher = Cipher.getInstance("RSA",
				new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
		// 加密块大小为127
		// byte,加密后为128个byte;因此共有2个加密块，第一个127
		// byte第二个为1个byte
		int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
		int leavedSize = data.length % blockSize;
		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
				: data.length / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (data.length - i * blockSize > 0) {
			if (data.length - i * blockSize > blockSize)
				cipher.doFinal(data, i * blockSize, blockSize, raw, i
						* outputSize);
			else
				cipher.doFinal(data, i * blockSize,
						data.length - i * blockSize, raw, i * outputSize);
			// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
			// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
			// OutputSize所以只好用dofinal方法。

			i++;
		}
		return raw;
		// } catch (Exception e) {
		// throw new Exception(e.getMessage());
		// }
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		// try {
		Cipher cipher = Cipher.getInstance("RSA",
				new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, pk);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int j = 0;

		while (raw.length - j * blockSize > 0) {
			bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
			j++;
		}
		return bout.toByteArray();
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new Exception(e.getMessage());
		// }
	}

	public static String getCurrentModulus() throws Exception {
		if (RSAUtil.pairs.isEmpty()) {
			// try {
			RSAUtil.generateKeyPair();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}
		RSAPublicKey rpu = (RSAPublicKey) RSAUtil.pairs.get(0).getPublic();
		return rpu.getModulus().toString(16);
	}

	public static String getCurrentPublicExponent() throws Exception {
		if (RSAUtil.pairs.isEmpty()) {
			// try {
			RSAUtil.generateKeyPair();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}
		RSAPublicKey rpu = (RSAPublicKey) RSAUtil.pairs.get(0).getPublic();
		return rpu.getPublicExponent().toString(16);
	}

	public static RSAPublicKey getCurrentPublicKey() {
		RSAPublicKey rpu = (RSAPublicKey) RSAUtil.pairs.get(0).getPublic();
		return rpu;
	}

	public static String getFitPrivateExponent(String publicExponent,
			String modulus) {
		for (int i = 0; i < RSAUtil.pairs.size(); i++) {
			RSAPublicKey rpu = (RSAPublicKey) RSAUtil.pairs.get(i).getPublic();
			if (rpu.getPublicExponent().toString(16).equals(publicExponent)
					&& rpu.getModulus().toString(16).equals(modulus)) {
				RSAPrivateKey rpri = (RSAPrivateKey) RSAUtil.pairs.get(i)
						.getPrivate();
				return rpri.getPrivateExponent().toString(16);
			}
		}
		return "";
	}

	public static PrivateKey getFitPrivate(String publicExponent, String modulus) {
		for (int i = 0; i < RSAUtil.pairs.size(); i++) {
			RSAPublicKey rpu = (RSAPublicKey) RSAUtil.pairs.get(i).getPublic();
			if (rpu.getPublicExponent().toString(16).equals(publicExponent)
					&& rpu.getModulus().toString(16).equals(modulus)) {
				return RSAUtil.pairs.get(i).getPrivate();
			}
		}
		return null;
	}

	public static String getResult(String publicExponent, String modulus,
			String code) throws Exception {
		PrivateKey pk = RSAUtil.getFitPrivate(publicExponent, modulus);
		// byte[] codeBytes = new BigInteger(code, 16).toByteArray();
		byte[] codeBytes = hexStringToBytes(code);
		StringBuffer sb = new StringBuffer();
		sb.append(new String(RSAUtil.decrypt(pk, codeBytes)));
		return sb.reverse().toString();
	}

	/**
	 * 16进制 To byte[]
	 * 
	 * @param hexString
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// RSAPublicKey rsap = (RSAPublicKey)
		// RSAUtil.generateKeyPair().getPublic();
		// String test = "ihep_公钥加密私钥解密hello world";
		// byte[] en_test = encrypt(getKeyPair().getPublic(), test.getBytes());
		// byte[] de_test = decrypt(getKeyPair().getPrivate(), en_test);
		// byte[] de_test2 = RSAEncrypt.decrypt((RSAPrivateKey)
		// getKeyPair().getPrivate(), en_test);
		// System.out.println(new String(en_test));
		// System.out.println(new String(de_test));
		// System.out.println(new String(de_test2));
		// for(int i=0;i<1;i++){
		// RSAUtil.generateKeyPair();
		// for(int j=0;j<RSAUtil.pairs.size();j++){
		// // RSAPublicKey rsap = (RSAPublicKey)
		// RSAUtil.generateKeyPair().getPublic();
		// RSAPrivateKey rpri =
		// (RSAPrivateKey)RSAUtil.pairs.get(j).getPrivate();
		// RSAPublicKey rpu = (RSAPublicKey)RSAUtil.pairs.get(j).getPublic();
		// // System.out.println(i+"_"+j+":"+rpri.getPrivateExponent());
		// System.out.println(i+"_"+j+":"+rpri);
		// System.out.println(i+"_"+j+":"+rpu);
		// // System.out.println(rsap.getModulus());
		// }
		// if(i==0){
		// // String test = "ihep_公钥加密私钥解密hello world";
		// // byte[] en_test = encrypt(RSAUtil.pairs.get(0).getPublic(),
		// test.getBytes());
		// // byte[] de_test = decrypt(RSAUtil.pairs.get(0).getPrivate(),
		// en_test);
		// // byte[] de_test2 = decrypt(RSAUtil.pairs.get(1).getPrivate(),
		// en_test);
		// // System.out.println(new String(en_test));
		// // System.out.println(new String(de_test));
		// // System.out.println(new String(de_test2));
		// System.out.println(RSAUtil.getCurrentModulus());
		// System.out.println(RSAUtil.getCurrentPublicExponent());
		// System.out.println(RSAUtil.getFitPrivateExponent(RSAUtil.getCurrentPublicExponent(),RSAUtil.getCurrentModulus()));
		// System.out.println(RSAUtil.getFitPrivateExponent(RSAUtil.getCurrentPublicExponent()+"sdf",RSAUtil.getCurrentModulus()));
		// }
		// }
		String code = "123123fdas";
		code = MD5Util.MD5(code);
		byte[] codeBytes = new BigInteger(code, 16).toByteArray();
		byte[] codeBytes2 = hexStringToBytes(code);
		System.out.println(codeBytes.length + "_" + codeBytes2.length);
		for (int i = 0; i < codeBytes.length; i++) {
			if (codeBytes[i] != codeBytes2[i]) {
				System.out
						.println(i + ":" + codeBytes[i] + "_" + codeBytes2[i]);
			}
		}
	}
}
