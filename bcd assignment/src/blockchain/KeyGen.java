package blockchain;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyGen {

	//testing
	public String algorithm = "RSA";
	
	private KeyPairGenerator keygen;
	private KeyPair keyPair;
	
	public KeyGen() {
		try {
			keygen = KeyPairGenerator.getInstance(algorithm);
			keygen.initialize(1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void create(String uuid)
	{
		KeyGen myKeyMaker = new KeyGen();
		myKeyMaker.keyPair = myKeyMaker.keygen.generateKeyPair();
		PublicKey pubKey = myKeyMaker.keyPair.getPublic();
		PrivateKey prvKey = myKeyMaker.keyPair.getPrivate();
		
		put( pubKey.getEncoded(), "KeyPair/PublicKey|"+uuid );
		put( prvKey.getEncoded(), "KeyPair/PrivateKey|"+uuid );
		
	}
	
	public static void put( byte[] keyBytes, String path )
	{
		File f = new File(path );
		f.getParentFile().mkdirs();
		try {
			Files.write(Paths.get(path), keyBytes, StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
