package Controller;

import java.security.*;
import java.util.Base64;

public class SignatureController {
	private Signature sig;
	private KeyPairGenerator keygen;
	private KeyPair keyPair;
	{
		try {
			keygen = KeyPairGenerator.getInstance("RSA");
			keyPair = keygen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SignatureController() {
		super();
		try {
			sig = Signature.getInstance( "SHA256WithRSA" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sign(String data, PrivateKey priKey) throws Exception
	{
		sig.initSign(priKey);
		sig.update(data.getBytes());
		return Base64.getEncoder().encodeToString(sig.sign());
	}
	
	public boolean verify(String data, String signature) throws Exception
	{
		sig.initVerify(keyPair.getPublic());
		sig.update(data.getBytes());
		return sig.verify( Base64.getDecoder().decode(signature));
	}
}
