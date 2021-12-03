public class Healer extends Player
{
    public Healer(String name)
    {
        super(name);
        this.health = 80;
        weapon = new Weapon("Wand", 5);
    }

    @Override
    public String getClassName() {
        return "Healer";
    }
}
