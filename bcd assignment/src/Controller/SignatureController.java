package Controller;

import java.security.*;
import java.util.Base64;

import BCD.Order;
import blockchain.Hasher;

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
	
	public String SignData(Order o) {
		String hashID = Hasher.newhash(o.getOrderID(), "MD5");
		String hashContact = Hasher.newhash(o.getUserContact().toString(), "MD5");
		String hashAddress = Hasher.newhash(o.getUserAddress(), "MD5");
		String hashItem = Hasher.newhash(Order.OrderItemString(o.getOrderItem()), "MD5");
		String hashAmount = Hasher.newhash(Double.toString(o.getPaymentAmount()), "MD5");
		String hashTime = Hasher.newhash(o.getOrderTime().toString(), "MD5");
		
		return hashID+hashContact+hashAddress+hashItem+hashAmount+hashTime;
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
