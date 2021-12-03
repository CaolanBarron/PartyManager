public class Rogue extends Player
{

    public Rogue(String name)
    {
        super(name);
        this.health = 80;
        weapon = new Weapon("Dagger", 20);
    }

    @Override
    public String getClassName() {
        return "Rogue";
    }
}
