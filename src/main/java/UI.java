import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UI extends JFrame {
    JPanel panel;
    JTextArea textAreaInformation;
    JScrollPane scrollArea;
    JButton buttonUrl;
    JTextField textboxURL;

    public UI() {
        panel = new JPanel();
        textAreaInformation = new JTextArea();
        scrollArea = new JScrollPane(textAreaInformation);
        buttonUrl = new JButton();
        textboxURL = new JTextField();
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
        textAreaInformation.setText("Information of Visual Recognition");
        textAreaInformation.setOpaque(true);
        textAreaInformation.setForeground(Color.WHITE);
        textAreaInformation.setBackground(Color.BLACK);
        scrollArea.setBounds(new Rectangle(10,10,400,500));
        panel.add(scrollArea);

        //Image of url TODO

    }

    private void initializationButtons() {
        buttonUrl.setText("Add Url");
        buttonUrl.setBounds(550, 80, 100, 40);
        panel.add(buttonUrl);
        ActionListener inputUrl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // "https://cumbrepuebloscop20.org/wp-content/uploads/2018/09/delfin.jpg" for test
                IamAuthenticator authenticator = new IamAuthenticator("DUpqPReP5pvTRlzDBx1wIzoR2Wt5rjDDh-xFkO18PMMV");
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder().url(textboxURL.getText()).build();
                VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);

                visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/b617a539-646c-420d-8f4e-5c8eb3694fc8");

                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
                textAreaInformation.setText(result.toString());
            }
        };
        buttonUrl.addActionListener(inputUrl);

    }

    private void initializationText() {
        textboxURL.setBounds(500, 160, 200, 30);
        textboxURL.getText();
        panel.add(textboxURL);
    }
}
