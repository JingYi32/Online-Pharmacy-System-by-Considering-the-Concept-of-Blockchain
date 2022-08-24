package Controller;

import java.util.List;
import java.util.stream.Collectors;

import blockchain.Hasher;

public class LoginController {
	
    public boolean verify(String username, String password) {
        String uuid = getUUID(username);
        if ( uuid == null ) return false;
        
        List<String> credential = getCredential( uuid );
        //20487f0d-5c5c-48f2-8928-67a042b4ddad|ja8yYZw98+eqHfFbHx5Ofg==|a45faea0afc0ead2ea8b66325b18cc2f
        String[] secret = credential.get(0).split("\\|");
        
        System.out.println( "Random: "+ secret[1] );  
        
        String hash = Hasher.newhash( Txt.append(secret[1], password), "md5" );
        
        System.out.println( "hash: "+ hash );  
        
        return hash.equalsIgnoreCase( secret[2] );
    }
    
    private List<String> getCredential( String uuid ){
        List<String> secretLst = ReadFile.read( "secret.txt" );
        return secretLst.stream()
               .filter( elem -> elem.startsWith(uuid))
               .collect(Collectors.toList());
    }
    
    private  String getUUID( String username ){
        List<String> usrLst = ReadFile.read( "login.txt" );
        return usrLst.stream()
                .filter( elem -> elem.startsWith(username) ) //user2|20487f0d-5c5c-48f2-8928-67a042b4ddad
                .map( elem -> elem.split("\\|")[1] )//20487f0d-5c5c-48f2-8928-67a042b4ddad
                .findAny()
                .orElse(null);
    }
}
