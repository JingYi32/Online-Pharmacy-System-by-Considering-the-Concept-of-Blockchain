package Controller;

import java.util.Base64;

import blockchain.*;

public class SigninController {
    private static final String FILE = "secret.txt";
    private static final String FILE_LOGIN = "login.txt";
    private static String uuid;
    
    public static void create( String username, String passwd ) throws Exception{
        String rand = Base64.getEncoder().encodeToString( Hasher.getSalt() );
        String hash = Hasher.newhash( Txt.append(rand, passwd),"MD5" );
        //write to file
        //FORMAT: UID|SALT|PASSWORDHASH
        ReadFile.write(FILE, String.join("|", uuid, rand, hash));
        //FORMAT: USERNAME|UUID
        ReadFile.write(FILE_LOGIN, String.join("|", username, uuid));
        System.out.println( "rand: " + rand );
        System.out.println( "Password Hash: " + hash );
   }
}
