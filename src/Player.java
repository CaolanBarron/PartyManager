import java.io.Serializable;

public abstract class Player implements Serializable
{
    String name;
    int health;
    Weapon weapon;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public int getHealth(){return this.health;}
    public void setHealth(int health){this.health = health;}

    public abstract String getClassName();
}


class Weapon implements Serializable
{
    String name;
    int attackDmg;

    Weapon(String name, int attackDmg)
    {
        this.name = name;
        this.attackDmg = attackDmg;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAttackDmg() {return attackDmg;}
    public void setAttackDmg(int attackDmg) {this.attackDmg = attackDmg;}
}