package blockchain;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymmetricKeyPair {
	
	protected Cipher cipher;

	public AsymmetricKeyPair(String param) {
		try {
			cipher = Cipher.getInstance( param );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String data, Key key) throws Exception {
		String cipherText = null;
		//init
		cipher.init(Cipher.ENCRYPT_MODE, (PublicKey)key);
		//encrypt
		byte[] cipherBytes = cipher.doFinal( data.getBytes() );
		//convert to string
		cipherText = Base64.getEncoder().encodeToString(cipherBytes);
		return cipherText;
	}

	public String decrypt(String cipherText, Key key) throws Exception {
		//init
		cipher.init(Cipher.DECRYPT_MODE, (PrivateKey)key);
		//convert to byte[]
		byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
		//decrypt
		byte[] dataBytes = cipher.doFinal( cipherBytes );  
		return new String( dataBytes );
	}
	
	public static PublicKey getPublicKey(String path) throws Exception
	{
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		return KeyFactory.getInstance("RSA").generatePublic(spec);
	}
	
	public static PrivateKey getPrivateKey(String path) throws Exception
	{
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		return KeyFactory.getInstance("RSA").generatePrivate(spec);
	}
}
