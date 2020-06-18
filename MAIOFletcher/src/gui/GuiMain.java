package gui;

import data.ScriptData;
import data.cut.CutProduct;
import data.string.StringProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: Logo? Fletching icon?
public class GuiMain{

    private JTable taskTable;
    private JFrame frame;
    private boolean started;

    public GuiMain() {

        frame = new JFrame();

        frame.setSize(800,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setTitle(ScriptData.scriptName);

        List<String> cutProductNames = Stream.of(CutProduct.values()).map(CutProduct::name).collect(Collectors.toList());
        List<String> stringProductNames = Stream.of(StringProduct.values()).map(StringProduct::name).collect(Collectors.toList());

        HashMap<String, List<String>> productionMap = new HashMap<>();
        productionMap.put("Cut", cutProductNames);
        productionMap.put("String", stringProductNames);

        //Fletching methods
        JComboBox methodComboBox = new JComboBox<>(productionMap.keySet().toArray());
        methodComboBox.setBounds(60,320,120,20);
        JLabel methodLabel = new JLabel("Method");
        methodLabel.setBounds(90,295,90,20);

        //Fletching products
        JComboBox productComboBox = new JComboBox(new DefaultComboBoxModel());
        productComboBox.setBounds(200,320,120,20);
        JLabel productLabel = new JLabel("Product");
        productLabel.setBounds(230, 295, 90, 20);

        //TODO: Input validation for integer-only. (I'll assume users aren't idiots, but lets see what happens)
        //Amount of actions
        JTextField amountTextBox = new JTextField();
        amountTextBox.setBounds(130,380,120,20);
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBounds(170,360,90,20);

        //Start button
        JButton startButton = new JButton("Start");
        startButton.setBounds(40,420,320,40);

        //Export config button
        JButton exportButton = new JButton("Export");
        exportButton.setBounds(210,480,150,20);

        //Import config button
        JButton importButton = new JButton("Import");
        importButton.setBounds(40,480,150,20);

        //Add task button
        JButton addButton = new JButton(">>>");
        addButton.setBounds(370, 320, 100, 25);

        //Delete task button
        JButton deleteButton = new JButton("<<<");
        deleteButton.setBounds(370,360,100,25);

        //TODO: Format text better in code editor.
        //Script information.
        JLabel scriptInformation = new JLabel("<HTML>Welcome to Morgan's AIO Fletcher v" + ScriptData.scriptVer + "<BR>" +
                "This script supports all methods of fletching (cutting/stringing/arrows..etc) <BR>" +
                "<BR>" +
                "You can queue fletching tasks, GE interaction by configuring them in the GUI <BR>" +
                "Use the arrow keys on the right hand side to add/remove tasks from the task list <BR>" +
                "<BR>" +
                "This script does support script arguments to launch, use the export config <BR>" +
                "option and add -config config.txt to your script arguments. <BR>" +
                "<BR>" +
                "The script is open-source and can be found here: <BR>" +
                "             " + ScriptData.githubLink +"               <BR>" +
                "Feedback is greatly appreciated: <BR>" +
                ScriptData.forumLink +"</HTML>");
        scriptInformation.setBounds(20,10,500,300);

        //All code for the task table shown at the right hand side.
        taskTable = new JTable();
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        taskTable.setShowVerticalLines(true);
        taskTable.setRowSelectionAllowed(true);
        DefaultTableModel taskTableModel = new DefaultTableModel(0,0);
        String[] columnHeaders = new String[] {"Task Type", "Product", "Amount"};
        taskTableModel.setColumnIdentifiers(columnHeaders);
        taskTable.setModel(taskTableModel);
        JScrollPane taskTableScrollPane = new JScrollPane(taskTable);
        taskTableScrollPane.setBounds(480,40,300,500);

        JLabel taskTableLabel = new JLabel("Task list");
        taskTableLabel.setBounds(620, 10, 100, 20);

        methodComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) methodComboBox.getSelectedItem();
                List<String> values = productionMap.get(selected);

                DefaultComboBoxModel model = (DefaultComboBoxModel) productComboBox.getModel();
                model.removeAllElements();
                for(String s : values) {
                    model.addElement(s);
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose location to export config to");

                int response = fileChooser.showSaveDialog(null);
                if(response == JFileChooser.APPROVE_OPTION) {
                    try {

                        String config = "";
                        String[][] tasks = getTasks();
                        for(String[] s : tasks) {
                            config += s[0] + ",";
                            config += s[1] + ",";
                            config += s[2] + ";" ;
                        }

                        FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
                        fw.write(config);
                        fw.close();

                    }catch(Exception exception) {
                        exception.printStackTrace();
                        //TODO: Complete exception handling
                    }
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select AIO Fletcher config file");

                int response = fileChooser.showSaveDialog(null);
                if(response == JFileChooser.APPROVE_OPTION) {
                    try {
                        String configData = "";
                        File configFile = fileChooser.getSelectedFile();
                        Scanner configScanner = new Scanner(configFile);
                        while(configScanner.hasNext()) {
                            configData += configScanner.nextLine();
                        }

                        //TODO: File parsing to ensure config file is actually a config file
                        String[] tasks = configData.split(";");
                        //System.out.println("Configuration file has " + tasks.length + " tasks");
                        for(String s : tasks) {
                            String[] task = s.split(",");
                            if(task.length == 3) {
                                taskTableModel.addRow(task);
                            }else {
                                System.out.println("Invalid config file");
                            }
                        }
                    }catch (Exception exception) {
                        exception.printStackTrace();
                        //TODO: Complete exception handling.
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    taskTableModel.removeRow(taskTable.getSelectedRow());
                }catch(ArrayIndexOutOfBoundsException exception) {
                    //TODO: Some kind of user notification. Maybe flash the table red?
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String method = (String) methodComboBox.getSelectedItem();
                String product = (String) productComboBox.getSelectedItem();
                String amount =  amountTextBox.getText();

                if(method != null && product != null && !amount.equals("")) {
                    taskTableModel.addRow(new String[]{method, product, amount});
                } //TODO: Some kind of user notification. Maybe flash the input boxes red?
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                started = true;
                frame.setVisible(false);
                frame.dispose();
            }
        });

        frame.add(methodComboBox);
        frame.add(methodLabel);
        frame.add(productComboBox);
        frame.add(productLabel);
        frame.add(amountTextBox);
        frame.add(amountLabel);
        frame.add(taskTableScrollPane);
        frame.add(taskTableLabel);
        frame.add(addButton);
        frame.add(deleteButton);
        frame.add(startButton);
        frame.add(importButton);
        frame.add(exportButton);
        frame.add(scriptInformation);

        frame.setVisible(true);

    }

    //TODO: There is a better way to do this...
    public String[][] getTasks() {
        int totalTasks = taskTable.getRowCount();
        String[][] tasks = new String[totalTasks][3];
        for(int i = 0; i < totalTasks; i++) {
            String[] task = new String[3];
            task[0] = (String) taskTable.getValueAt(i, 0);
            task[1] = (String) taskTable.getValueAt(i, 1);
            task[2] = (String) taskTable.getValueAt(i, 2);
            tasks[i] = task;
        }
        return tasks;
    }

    public boolean isStarted() {
        return this.started;
    }
}
