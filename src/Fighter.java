import java.rmi.RemoteException;
import java.util.Random;

public class Fighter extends Player
{
    public Fighter(String name) throws RemoteException {
        super(name);
        this.health = 100;
        weapon = new Weapon("Sword", 25);
    }

    @Override
    public String getClassName()
    {
        return "Fighter";
    }


}
