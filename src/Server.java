import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class Server implements ActionListener
{
    List<Player> players;
    JFrame frame;

    JPanel interactPanel;
    JTextField namePicker;
    JComboBox classPicker;
    JButton addPlayer;
    JButton savePlayers;

    JPanel contentPanel;
    JScrollPane scrollPane;

    public Server()
    {
        players = new ArrayList<Player>();
        frame = new JFrame("Server");
        interactPanel = new JPanel();
        frame.setLocation(100,100);
        frame.setSize(500,500);

        namePicker = new JTextField("Name");
        interactPanel.add(namePicker);

        String[] classes = {"Fighter", "Rogue", "Healer"};
        classPicker = new JComboBox(classes);
        interactPanel.add(classPicker);

        addPlayer = new JButton("Add Player");
        addPlayer.addActionListener(this);
        interactPanel.add(addPlayer);

        savePlayers = new JButton("Save");
        savePlayers.addActionListener(this);
        interactPanel.add(savePlayers);

        frame.add(interactPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        scrollPane = new JScrollPane(contentPanel);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

        InitPlayers();
    }

    public void addPlayer(Player p)
    {
        contentPanel.add(new CharacterPanel(p, this));
        contentPanel.updateUI();
    }

    private void EditPlayer(GenericButton j)
    {
        j.player.setName(JOptionPane.showInputDialog("Edit Name"));
        CharacterPanel temp = (CharacterPanel) j.getPanel();
        temp.setPlayerName(j.player.getName());
        contentPanel.updateUI();
    }

    public void removePlayer(GenericButton d)
    {
        players.remove(d.getPlayer());
        contentPanel.remove(d.getPanel());
        contentPanel.updateUI();
    }

    public void InitPlayers()
    {
        Deserialize();
        for(Player p : players)
        {
            addPlayer(p);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if (e.getSource() == addPlayer)
            {
                Player tempPlayer = PlayerFactory.playerFactory(classPicker.getSelectedIndex(), namePicker.getText());
                addPlayer(tempPlayer);
                players.add(tempPlayer);
            }
            else if (e.getSource() == savePlayers)
            {
                SerializeAll();
            }
            else if(e.getActionCommand() == "Delete")
            {
                removePlayer((GenericButton) e.getSource());
            }
            else if(e.getActionCommand() == "Edit")
            {
                EditPlayer((GenericButton) e.getSource());
            }
        }
        catch (ClassCastException cce)
        {
            System.out.println("Not yet Implemented");
        }
    }

    public static void main(String[] args)
    {
        Server s = new Server();

    }

    public void SerializeAll()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("players.ser",false);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for(Player p : players)
            {
                objectOut.writeObject(p);
            }

            objectOut.close();

            System.out.println("All classes serialised");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Deserialize()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("players.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            boolean cont = true;
            while(cont)
            {
                Player p = (Player)objectIn.readObject();

                if(p != null)
                    players.add(p);
                else
                    cont = false;
            }
            objectIn.close();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}



class CharacterPanel extends JPanel
{
    PlayerLabel playerName;

    public CharacterPanel(Player p, Server s)
    {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();


        PlayerLabel playerHeader = new PlayerLabel(p.getClassName(), Color.GRAY);
        c.gridx = 0; c.gridy = 0;
        this.add(playerHeader, c);

        PlayerLabel weaponHeader = new PlayerLabel("Wielding", Color.GRAY);
        c.gridx = 1; c.gridy = 0;
        this.add(weaponHeader, c);

        playerName = new PlayerLabel(p.getName(), Color.LIGHT_GRAY);
        c.gridx = 0; c.gridy = 1;
        this.add(playerName, c);

        PlayerLabel weaponName = new PlayerLabel(p.weapon.getName(), Color.LIGHT_GRAY);
        c.gridx = 1; c.gridy = 1;
        this.add(weaponName, c);

        PlayerLabel healthHeader = new PlayerLabel("Health", Color.GRAY);
        c.gridx = 0; c.gridy = 2;
        this.add(healthHeader, c);

        PlayerLabel attackHeader = new PlayerLabel("Attack Damage", Color.GRAY);
        c.gridx = 1; c.gridy = 2;
        this.add(attackHeader, c);

        PlayerLabel playerHealth = new PlayerLabel(String.valueOf(p.getHealth()), Color.LIGHT_GRAY);
        c.gridx = 0; c.gridy = 3;
        this.add(playerHealth, c);

        PlayerLabel weaponAttack = new PlayerLabel(String.valueOf(p.weapon.getAttackDmg()), Color.LIGHT_GRAY);
        c.gridx = 1; c.gridy = 3;
        this.add(weaponAttack, c);

        GenericButton editButton = new GenericButton("Edit", this, p);
        editButton.addActionListener(s);
        c.gridx = 2; c.gridy = 1;
        c.gridheight = 1;
        editButton.setPreferredSize(new Dimension(80,25));
        this.add(editButton,c);

        GenericButton deleteButton = new GenericButton("Delete", this, p);
        deleteButton.addActionListener(s);
        c.gridx = 2; c.gridy = 2;
        c.gridheight = 1;
        deleteButton.setPreferredSize(new Dimension(80,25));
        this.add(deleteButton, c);

        this.setBorder(BorderFactory.createBevelBorder(0));
        this.setBorder(new EmptyBorder(10,0,10,0));
    }

    public PlayerLabel getPlayerName(){return this.playerName;}
    public void setPlayerName(String playerName) {this.playerName.setText(playerName);}
}

class GenericButton extends JButton
{
    JPanel parent;
    Player player;

    GenericButton(String text, JPanel parent, Player player)
    {
        super(text);
        this.parent = parent;
        this.player = player;
    }

    public JPanel getPanel()
    {
        return parent;
    }
    public Player getPlayer(){return player;}
}
class PlayerLabel extends JLabel
{
    Font f = new Font("Font", 0, 20);
    PlayerLabel(String text, Color c)
    {
        super(text);
        this.setPreferredSize(new Dimension(150,25));
        this.setFont(f);
        this.setOpaque(true);
        this.setBackground(c);
    }
}