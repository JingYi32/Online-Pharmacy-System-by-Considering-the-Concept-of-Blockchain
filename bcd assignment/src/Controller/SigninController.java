package Controller;

import java.util.Base64;
import java.util.UUID;

import blockchain.*;

public class SigninController {
    private static final String FILE = "secret.txt";
    private static final String FILE_LOGIN = "login.txt";
    private static String uuid;
    static{
        uuid = UUID.randomUUID().toString();
        System.out.println( uuid );
        
    }
    
    public static void create( String username, String passwd ) throws Exception{
        String rand = Base64.getEncoder().encodeToString( Hasher.getSalt() );
        String hash = Hasher.newhash( Txt.append(rand, passwd),"MD5" );
        ReadFile.write(FILE, String.join("|", uuid, rand, hash));
        ReadFile.write(FILE_LOGIN, String.join("|", username, uuid));
   }
}
