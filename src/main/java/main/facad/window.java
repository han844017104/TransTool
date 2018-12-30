/*
 * Created by JFormDesigner on Fri Dec 28 20:10:38 GMT+08:00 2018
 */

package main.facad;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class window extends JFrame {
    public window() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("OK");
                buttonBar.add(okButton, "cell 0 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
