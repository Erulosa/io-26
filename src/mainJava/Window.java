package mainJava;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window extends JFrame {
    public static int shopIndex;
    public static DataBase dataBase;
    private static InteractiveTableModel tableModel;
    private JPanel jp1, jp2, jp3;
    private DefaultListModel<String> listModel;
    private JButton addCButton, delButton, editButton;
    private JList<String> chooseShop1, chooseShop2;
    private InteractiveForm iForm1, iForm2;
    public Window() {
        dataBase = new DataBase();
        listModel = new DefaultListModel<String>();
        tableModel = new InteractiveTableModel(new String[]{"Товар магазину", "Кількість", "Ціна", ""});
        shopIndex = -1;
        createWindow();
    }
    public static void setCommodities(int row, int column) {
        if (shopIndex == -1 || row >= dataBase.getShop(shopIndex).getCommodityCount()) return;
        Commodity commodity = dataBase.getShop(shopIndex).getCommodity(row);
        switch (column) {
            case 0:
                commodity.setName((String) tableModel.getValueAt(row, column));
                break;
            case 1:
                commodity.setCount((Integer) tableModel.getValueAt(row, column));
                break;
            case 2:
                commodity.setPrice((Double) tableModel.getValueAt(row, column));
        }
    }

    private void createWindow() {
        setResizable(false);
        setTitle("Мережа квіткових магазинів");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 510);
   setLocationRelativeTo(null);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        JTabbedPane jtp = new JTabbedPane();
        add(jtp);
        jtp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateShops();
                addCButton.setEnabled(shopIndex != -1);
                delButton.setEnabled(shopIndex != -1);
                editButton.setEnabled(shopIndex != -1);
                if (shopIndex != -1) {
                    chooseShop1.setSelectionInterval(shopIndex, shopIndex);
                    chooseShop2.setSelectionInterval(shopIndex, shopIndex);
                }
            }
        });
        createOpenTab();
        createEditTab();
        createSaveTab();
        jtp.addTab("Відкрити дані мережі", jp1);
        jtp.addTab("Редагувати дані", jp2);
        jtp.addTab("Зберегти", jp3);
        setVisible(true);
    }
    private void updateShops() {
        listModel.clear();
        for (int i = 0; i < dataBase.getShopsCount(); i++) {
            listModel.addElement(dataBase.getShop(i).getShopName());
        }
    }
    private void updateCommodity(int shopIndex) {
        if (shopIndex != -1) {
            if (tableModel.getRowCount() > 0) {
                tableModel.deleteRows();
                tableModel.dataVector.clear();
            }
            for (int i = 0; i < dataBase.getShop(shopIndex).getCommodityCount(); i++) {
                if (tableModel.getRowCount() < dataBase.getShop(shopIndex).getCommodityCount())
                    tableModel.addEmptyRow();
                Commodity currentCommodity = dataBase.getShop(shopIndex).getCommodity(i);
                tableModel.setValueAt(currentCommodity.getName(), i, 0);
                tableModel.setValueAt(currentCommodity.getCount(), i, 1);
                tableModel.setValueAt(currentCommodity.getPrice(), i, 2);
            }
        }
    }
    private void createOpenTab() {
        jp1.setLayout(null);
        JLabel label1 = new JLabel("Вибір магазину:");
        label1.setBounds(10, 10, 100, 20);
        chooseShop1 = new JList<String>(listModel);
        chooseShop1.setBounds(10, 30, 200, 370);
        chooseShop1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (chooseShop1.getSelectedIndex() != -1) {
                    shopIndex = chooseShop1.getSelectedIndex();
                    updateCommodity(shopIndex);
                    addCButton.setEnabled(shopIndex != -1);
                    delButton.setEnabled(shopIndex != -1);
                    editButton.setEnabled(shopIndex != -1);
                }
            }
        });
        iForm1 = new InteractiveForm(tableModel, false);
        iForm1.setBounds(220, 30, 400, 370);
        JButton exitButton = new JButton("Завершити роботу");
        exitButton.setBounds(470, 410, 150, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        updateShops();
        jp1.add(iForm1);
        jp1.add(exitButton);
        jp1.add(chooseShop1);
        jp1.add(label1);
    }
    private void createEditTab() {
        jp2.setLayout(null);
        JLabel label1 = new JLabel("Вибір магазину:");
        label1.setBounds(10, 10, 100, 20);
        delButton = new JButton("<html><center> Видалити магазин</center></html>");
   delButton.setEnabled(false);
        JButton addButton = new JButton("<html><center>Додати магазин</center></html>");
        addButton.setBounds(10, 410, 90, 35);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addShop();
            }
        });
        chooseShop2 = new JList<String>(listModel);
        chooseShop2.setBounds(10, 30, 200, 370);
        chooseShop2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (chooseShop2.getSelectedIndex() != -1) {
                    shopIndex = chooseShop2.getSelectedIndex();
                    updateCommodity(shopIndex);
                    addCButton.setEnabled(shopIndex != -1);
                    delButton.setEnabled(shopIndex != -1);
                    editButton.setEnabled(shopIndex != -1);
                }
            }
        });
        delButton.setBounds(105, 410, 90, 35);
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooseShop2.getSelectedIndex() != -1) {
                    shopIndex = -1;
                    dataBase.removeShop(chooseShop2.getSelectedIndex());
                    updateShops();
                    tableModel.deleteRows();
                    tableModel.dataVector.clear();
                }
            }
        });
        editButton = new JButton("<html><center>Редагувати магазин</center></html>");
        editButton.setBounds(200, 410, 90, 35);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooseShop2.getSelectedIndex() != -1)
                    editShop(dataBase.getShop(chooseShop2.getSelectedIndex()));
            }
        });
        addCButton = new JButton("<html><center>Додати товар</center></html>");
        addCButton.setEnabled(false);
        addCButton.setBounds(295, 410, 90, 35);
        addCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooseShop2.getSelectedIndex() != -1)
                    addCommodity();
            }
        });
        JButton delCButton = new JButton("<html><center>Видалити товар</center></html>");
        delCButton.setBounds(390, 410, 90, 35);
        delCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((iForm1.getTable().getSelectedRow() != -1 || iForm2.getTable().getSelectedRow() != -1) && shopIndex != -1) {
                    dataBase.getShop(shopIndex).removeCommodity(iForm2.getTable().getSelectedRow());
                    updateCommodity(shopIndex);
                }
            }
        });
        iForm2 = new InteractiveForm(tableModel, true);
        iForm2.setBounds(220, 30, 400, 370);
        JButton exitButton = new JButton("<html><center><Завершити роботу</center></html>");
        exitButton.setBounds(485, 410, 135, 35);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jp2.add(delCButton);
        jp2.add(addCButton);
        jp2.add(editButton);
        jp2.add(delButton);
        jp2.add(iForm2);
        jp2.add(exitButton);
        jp2.add(addButton);
        jp2.add(chooseShop2);
        jp2.add(label1);
    }
    private void createSaveTab() {
        jp3.setLayout(null);
        final JLabel jLabel = new JLabel("");
        jLabel.setBounds(0, 10, 640, 30);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton saveButton = new JButton("Зберегти");
        saveButton.setBounds(270, 50, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SaveLoadData.saveData(dataBase);
                    jLabel.setText("Данні успішно збережені!");
                    jLabel.setForeground(new Color(0, 228, 0));
                } catch (IOException e1) {
                    jLabel.setText("Помилка при збереженні!");
                    jLabel.setForeground(Color.RED);
                }
            }
        });
        JButton exitButton = new JButton("<html><center>Завершити роботу</center></html>");
        exitButton.setBounds(270, 95, 100, 35);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jp3.add(jLabel);
        jp3.add(exitButton);
        jp3.add(saveButton);
    }
    private void addShop() {
        JPanel dp = new JPanel(null);
        final JDialog dialog = new JDialog(this, true);
        dialog.setResizable(false);
        dialog.setTitle("Новий магазин");
        dialog.setSize(300, 160);
        dialog.setLocationRelativeTo(null);
        JLabel jlabel = new JLabel("Введіть назву магазину:");
        jlabel.setBounds(60, 10, 180, 20);
        final JButton createButton = new JButton("Створити");
        final JTextField tfield = new JTextField();
        tfield.setBounds(10, 35, 275, 30);
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfield.getText().length() > 0) {
                    if (dataBase.containsShop(tfield.getText())) {
                        tfield.setBackground(new Color(255, 100, 100));
                    } else {
                        dataBase.addShop(new Shop(tfield.getText()));
                        updateShops();
                        dialog.dispose();
        }}}};
        tfield.addActionListener(al);
        tfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                createButton.setEnabled(tfield.getText().length() != 0);
                tfield.setBackground(Color.white);
            }
        });
        createButton.setEnabled(false);
        createButton.setBounds(10, 80, 135, 30);
        createButton.addActionListener(al);
        JButton cancelButton = new JButton("Скасувати");
        cancelButton.setBounds(155, 80, 135, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dp.add(jlabel);
        dp.add(cancelButton);
        dp.add(createButton);
        dp.add(tfield);
        dialog.add(dp);
        dialog.setVisible(true);
    }
    private void editShop(final Shop shop) {
        JPanel dp = new JPanel(null);
        final JDialog dialog = new JDialog(this, true);
        dialog.setResizable(false);
        dialog.setTitle("Редагувати магазин");
        dialog.setSize(300, 160);
        dialog.setLocationRelativeTo(null);
        JLabel jlabel = new JLabel("Введіть нову назву магазину:");
        jlabel.setBounds(10, 10, 180, 20);
        final JButton createButton = new JButton("Зберегти");
        final JTextField tfield = new JTextField(shop.getShopName());
        tfield.setBounds(10, 35, 275, 30);
        final String originalName = shop.getShopName();
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfield.getText().length() > 0) {
                    if (dataBase.containsShop(tfield.getText()) && !tfield.getText().equals(originalName)) {
                        tfield.setBackground(new Color(255, 100, 100));
                    } else {
                        shop.setShopName(tfield.getText());
                        updateShops();
                        dialog.dispose();
                    }
         } }};
        tfield.addActionListener(al);
        tfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                createButton.setEnabled(tfield.getText().length() != 0);
                tfield.setBackground(Color.white);
            }
        });

        createButton.setBounds(10, 80, 135, 30);
        createButton.addActionListener(al);
        JButton cancelButton = new JButton("Скасувати");
        cancelButton.setBounds(155, 80, 135, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dp.add(jlabel);
        dp.add(cancelButton);
        dp.add(createButton);
        dp.add(tfield);
        dialog.add(dp);
        dialog.setVisible(true);
    }
    private void addCommodity() {
        JPanel dp = new JPanel(null);
        final JDialog dialog = new JDialog(this, true);
        dialog.setResizable(false);
        dialog.setTitle("Додати товар");
        dialog.setSize(250, 160);
        dialog.setLocationRelativeTo(null);

        JLabel jlabel = new JLabel("Назва квітів: ");
        jlabel.setBounds(10, 10, 90, 25);
        JLabel countLabel = new JLabel("Кількість: ");
        countLabel.setBounds(10, 40, 90, 25);
        JLabel priceLabel = new JLabel("Ціна");
        priceLabel.setBounds(10, 70, 90, 25);
        final JTextField tCName = new JTextField();
        tCName.setBounds(90, 10, 150, 25);
        tCName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                tCName.setBackground(Color.white);
            }
        });
        final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, null, Integer.MAX_VALUE, 1);
        final JSpinner countSpinner = new JSpinner(spinnerModel);
        countSpinner.setBounds(90, 40, 150, 25);
        final JTextField priceField = new JTextField();
        priceField.setBounds(90, 70, 150, 25);
        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                priceField.setBackground(Color.white);
            }
        });
        JButton addButton = new JButton("Додати");
        addButton.setBounds(10, 100, 112, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCommodity(tCName, spinnerModel, priceField, dialog);
            }
        });
        tCName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCommodity(tCName, spinnerModel, priceField, dialog);
            }
        });
        priceField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCommodity(tCName, spinnerModel, priceField, dialog);
            }
        });
        JButton cancelButton = new JButton("Скасувати");
        cancelButton.setBounds(125, 100, 112, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dp.add(addButton);
        dp.add(priceField);
        dp.add(countSpinner);
        dp.add(tCName);
        dp.add(jlabel);
        dp.add(cancelButton);
        dp.add(countLabel);
        dp.add(priceLabel);
        dialog.add(dp);
        dialog.setVisible(true);
    }
    private void addCommodity(JTextField tCName, SpinnerNumberModel spinnerModel, JTextField priceField, JDialog dialog) {
        boolean check = true;
        if (tCName.getText().length() == 0) {
            tCName.setBackground(new Color(255, 100, 100));
            check = false;
        }
        int count = (Integer) spinnerModel.getValue();
        double price = 0;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (Exception ex) {
            priceField.setBackground(new Color(255, 100, 100));
            check = false;
        }
        if (check) {
            dataBase.getShop(shopIndex).addCommodity(new Commodity(tCName.getText(), count, price));
            dialog.dispose();
            updateCommodity(shopIndex);
        }
    }
    public void loadDataBase(DataBase dataBase) {
        Window.dataBase = dataBase;
        updateShops();
    }
}
