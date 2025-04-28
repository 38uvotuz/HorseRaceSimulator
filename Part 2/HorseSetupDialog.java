import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HorseSetupDialog extends JDialog {
    private HorseGUI[] horses;
    private JTextField[] nameFields;
    private JComboBox<String>[] breedBoxes;
    private JComboBox<String>[] colorBoxes;
    private JComboBox<String>[] symbolBoxes;
    private JComboBox<String>[] saddleBoxes;
    private JComboBox<String>[] horseshoesBoxes;

    public HorseSetupDialog(JFrame parent, HorseGUI[] horses) {
        super(parent, "Horse Setup", true);
        this.horses = horses;

        setSize(600, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(horses.length * 6, 2));

        nameFields = new JTextField[horses.length];
        breedBoxes = new JComboBox[horses.length];
        colorBoxes = new JComboBox[horses.length];
        symbolBoxes = new JComboBox[horses.length];
        saddleBoxes = new JComboBox[horses.length];
        horseshoesBoxes = new JComboBox[horses.length];

        String[] breeds = {"Arabian", "Mustang", "Thoroughbred"};
        String[] colors = {"Black", "Brown", "White", "Grey"};
        String[] symbols = {"♞", "🐎", "🐴", "🏇"};
        String[] saddles = {"Standard Saddle", "Racing Saddle", "Heavy Saddle"};
        String[] horseshoes = {"Standard Shoes", "Grip Shoes", "Lightweight Shoes"};

        for (int i = 0; i < horses.length; i++) {
            nameFields[i] = new JTextField("Horse" + (i + 1));
            breedBoxes[i] = new JComboBox<>(breeds);
            colorBoxes[i] = new JComboBox<>(colors);
            symbolBoxes[i] = new JComboBox<>(symbols);
            saddleBoxes[i] = new JComboBox<>(saddles);
            horseshoesBoxes[i] = new JComboBox<>(horseshoes);

            panel.add(new JLabel("Horse " + (i + 1) + " Name:"));
            panel.add(nameFields[i]);
            panel.add(new JLabel("Breed:"));
            panel.add(breedBoxes[i]);
            panel.add(new JLabel("Color:"));
            panel.add(colorBoxes[i]);
            panel.add(new JLabel("Symbol:"));
            panel.add(symbolBoxes[i]);
            panel.add(new JLabel("Saddle:"));
            panel.add(saddleBoxes[i]);
            panel.add(new JLabel("Horseshoes:"));
            panel.add(horseshoesBoxes[i]);
        }

        JButton saveButton = new JButton("Save and Start");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < horses.length; i++) {
                    String name = nameFields[i].getText();
                    String breed = (String) breedBoxes[i].getSelectedItem();
                    String color = (String) colorBoxes[i].getSelectedItem();
                    char symbol = ((String) symbolBoxes[i].getSelectedItem()).charAt(0);
                    String saddle = (String) saddleBoxes[i].getSelectedItem();
                    String horseshoe = (String) horseshoesBoxes[i].getSelectedItem();

                    double confidence = 0.8 + Math.random() * 0.2;

                    horses[i] = new HorseGUI(name, confidence, breed, color, symbol, saddle, horseshoe, i);
                }
                dispose();
            }
        });

        add(new JScrollPane(panel), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }
}
