
public class Fighter extends Player
{
    public Fighter(String name)
    {
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
