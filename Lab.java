import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Lab extends Frame {
    TextArea displaySearchResult;
    TextField searchKey;
    Choice choice = new Choice();
    CardLayout cl = new CardLayout();
    Panel mainPanel;
    DatabaseManagement dbm;

    Lab(String title) throws SQLException, ClassNotFoundException {
        super(title);
        setVisible(true);
        setSize(500, 500);
        closeWin();
        dbm = new DatabaseManagement();
        mainPanel = new Panel(cl);
        add(mainPanel);
        cardBtn();
        addCardSearch(mainPanel);
        addCardAdd(mainPanel);
        addCardDel(mainPanel);
    }

    Label inserted, deleted;

    private void addCardDel(Panel mainPanel) {
        TextField delTelephoneNumber;
        Panel p = new Panel(null);
        mainPanel.add(p, "Delete");
        Button delBtn;

        addComp(new Label("Telephone Number : "), 20, 50, 100, 25, p);
        addComp(delTelephoneNumber = new TextField(), 120, 50, 150, 25, p);

        addComp(delBtn = new Button("Delete"), 80, 100, 40, 25, p);
        deleted = new Label();
        delBtn.addActionListener((ActionEvent ae) -> {
            p.remove(deleted);
            int dialogBox = JOptionPane.showConfirmDialog(this,
                    "Sure? You want to Delete?",
                    "Delete Number",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == dialogBox) {
                try {
                    int t = dbm.deleteData(Long.parseLong(delTelephoneNumber.getText().trim()));
                    if (t == 0)
                        addComp(deleted = new Label("Not Found in Record Successfully !!!"), 20, 250, 400, 200, p);
                    else addComp(deleted = new Label("Deleted Successfully !!!"), 20, 250, 400, 200, p);
                } catch (SQLException e) {
                    addComp(deleted = new Label("Couldn't Delete !!!"), 20, 250, 400, 200, p);
//                    e.printStackTrace();
                }
            }
            delTelephoneNumber.setText("");
        });
    }

    private void addCardAdd(Panel mainPanel) {
        Panel p = new Panel(null);
        mainPanel.add(p, "Add");
        Button addBtn;
        TextField name, telephoneNumber, address;

        addComp(new Label("Telephone Number : "), 20, 50, 100, 25, p);
        addComp(telephoneNumber = new TextField(), 120, 50, 150, 25, p);

        addComp(new Label("Name : "), 20, 100, 100, 25, p);
        addComp(name = new TextField(), 120, 100, 150, 25, p);

        addComp(new Label("Address : "), 20, 150, 40, 25, p);
        addComp(address = new TextField(), 120, 150, 150, 25, p);

        addComp(addBtn = new Button("Save"), 80, 200, 40, 25, p);
        inserted = new Label();
        addBtn.addActionListener((ActionEvent ae) -> {
            p.remove(inserted);
            try {
                dbm.insertData(Long.parseLong(telephoneNumber.getText().trim()), name.getText(), address.getText());
                telephoneNumber.setText("");
                name.setText("");
                address.setText("");
                addComp(inserted = new Label("Inserted Successfully !!!"), 20, 250, 400, 200, p);
            } catch (SQLException e) {
                addComp(inserted = new Label("Couldn't Insert !!!"), 20, 250, 400, 200, p);
            }
        });
    }

    private void cardBtn() {
        Panel p = new Panel(new GridLayout(1, 3));
        add("North", p);
        Button bS, bA, bD;
        p.add(bS = new Button("Search"));
        p.add(bA = new Button("Add"));
        p.add(bD = new Button("Delete"));
        bS.addActionListener(ActionEvent -> cl.show(mainPanel, "Search"));
        bD.addActionListener(ActionEvent -> cl.show(mainPanel, "Delete"));
        bA.addActionListener(actionEvent -> cl.show(mainPanel, "Add"));
    }

    private void addCardSearch(Panel mP) {
        Button searchBtn;
        Panel p = new Panel(null);

        mP.add(p, "Search");
        addComp(new Label("Select : "), 20, 50, 40, 25, p);
        addSelectionList(p);

        addComp(new Label("Enter : "), 20, 100, 40, 25, p);
        addComp(searchKey = new TextField(), 100, 100, 150, 25, p);

        addComp(searchBtn = new Button("OK"), 100, 150, 40, 25, p);
        addComp(displaySearchResult = new TextArea(), 20, 200, 400, 200, p);

        searchBtn.addActionListener((ActionEvent ae) -> {
            displaySearchResult.setText("");
            String result;
            try {
                result = dbm.getData(searchKey.getText().trim(), choice.getSelectedItem());
                displaySearchResult.setText(result);
                searchKey.setText("");
            } catch (SQLException e) {
                searchKey.setText("DB not connected");
            }
        });
    }

    private void closeWin() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                if (dbm != null) dbm.closeConn();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
                if (dbm != null) dbm.closeConn();
            }
        });
    }

    private void addSelectionList(Panel p) {
        addComp(choice, 100, 50, 150, 25, p);
        choice.add("TelephoneNumber");
        choice.add("Name");
        choice.add("Address");
    }

    private void addComp(Component c, int x, int y, int w, int h, Panel p) {
        c.setBounds(x, y, w, h);
        p.add(c);
    }

    public static void main(String[] args) {
        try {
            new Lab("Telephone directory");
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("DB not connected...");
        }
    }
}
