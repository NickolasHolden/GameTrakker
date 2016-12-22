import java.io.File;
import java.io.IOException;
import net.ucanaccess.jdbc.JackcessOpenerInterface;
import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

/**
 * This class is needed to connect to a password protected database
 * @author Nickolas
 */
public class CryptCodecOpener implements JackcessOpenerInterface {
         @Override
    public Database open(File fl,String pwd) throws IOException {
       DatabaseBuilder dbd = new DatabaseBuilder(fl);
       dbd.setAutoSync(false);
       dbd.setCodecProvider(new CryptCodecProvider(pwd));
       dbd.setReadOnly(false);
       return dbd.open();
    } 
}