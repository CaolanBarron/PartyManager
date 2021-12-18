import java.rmi.RemoteException;

public class PlayerFactory
{
    public static Player playerFactory(int i, String namePicker) throws RemoteException {
        return switch (i)
        {
            case 0 -> new Fighter(namePicker);
            case 1 -> new Rogue(namePicker);
            case 2 -> new Healer(namePicker);
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
}
