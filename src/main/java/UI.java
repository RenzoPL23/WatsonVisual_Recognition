import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {
    JPanel panel;
    JButton buttonUrl;
    JTextField textboxURL;
    JTextField textboxKey;
    JTextField textboxService;
    DefaultTableModel model;
    JTable textAreaInformation;
    TableRowSorter<TableModel> sorter;
    List<RowSorter.SortKey> sortKeys;
    JLabel imageArea;
    JLabel labelUrl;
    JLabel labelKey;
    JLabel labelService;
    URL url;
    ImageIcon image;

    public UI() {
        panel = new JPanel();
        model = new DefaultTableModel();
        textAreaInformation = new JTable(model);
        imageArea = new JLabel();
        buttonUrl = new JButton();
        textboxURL = new JTextField();
        textboxKey = new JTextField();
        textboxService = new JTextField();
        labelUrl = new JLabel("URL");
        labelKey= new JLabel("Api Key");
        labelService = new JLabel("Url Service");

        initializationWindows();
        initializationComponents();
    }


    private void initializationWindows() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setTitle("Visual Recognition Watson");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializationComponents() {
        initializationPanel();
        informationArea();
        initializationButtons();
        initializationText();
    }

    private void initializationPanel() {
        panel.setLayout(null);
        this.getContentPane().add(panel);
    }

    private void informationArea() {
        // information of visual
        model.addColumn("Class");
        model.addColumn("Score");
        model.addRow(new Object[]{"Class", "Score"});
        textAreaInformation.setBounds(10, 10, 400, 500);

        panel.add(textAreaInformation);

        //Image
        imageArea.setBounds(450, 250, 300, 300);
        panel.add(imageArea);

    }

    private void initializationButtons() {
        buttonUrl.setText("Add Url");
        buttonUrl.setBounds(550, 80, 100, 40);
        panel.add(buttonUrl);
        ActionListener inputUrl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String className;
                Float scoreRecognition;
                //
                sorter = new TableRowSorter<TableModel>(model);
                sortKeys = new ArrayList<RowSorter.SortKey>(25);

                textAreaInformation.setRowSorter(sorter);
                sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
                sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
                sorter.setSortKeys(sortKeys);
                //

                Integer numberClasses;
                // "https://cumbrepuebloscop20.org/wp-content/uploads/2018/09/delfin.jpg" for test
                // https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/b617a539-646c-420d-8f4e-5c8eb3694fc8 for service
                // DUpqPReP5pvTRlzDBx1wIzoR2Wt5rjDDh-xFkO18PMMV api key
                IamAuthenticator authenticator = new IamAuthenticator(textboxKey.getText());
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder().url(textboxURL.getText()).build();
                VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);

                visualRecognition.setServiceUrl(textboxService.getText());

                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
                numberClasses = result.getImages().get(0).getClassifiers().get(0).getClasses().size();

                System.out.println(result.toString());
                try {
                    url = new URL(textboxURL.getText());
                    image = new ImageIcon(url);
                    imageArea.setIcon(new ImageIcon(image.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < numberClasses - 1; i++) {
                    className = result.getImages().get(0).getClassifiers().get(0).getClasses().get(i).getXClass();
                    scoreRecognition = result.getImages().get(0).getClassifiers().get(0).getClasses().get(i).getScore();
                    model.addRow(new Object[]{className, scoreRecognition});
                }
            }
        };
        buttonUrl.addActionListener(inputUrl);

    }

    private void initializationText() {
        labelUrl.setBounds(460-30,140,70,30);
        labelKey.setBounds(460-30,180,70,30);
        labelService.setBounds(460-30,220,70,30);


        textboxURL.setBounds(500, 140, 200, 30);
        textboxKey.setBounds(500,180,200,30);
        textboxService.setBounds(500,220,200,30);

        panel.add(labelUrl);
        panel.add(labelKey);
        panel.add(labelService);
        panel.add(textboxURL);
        panel.add(textboxKey);
        panel.add(textboxService);

    }
}
