import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote
{
    public String rollAbility() throws RemoteException;
}
