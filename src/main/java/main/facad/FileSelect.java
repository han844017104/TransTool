package main.facad;

import main.io.FileAppender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/30  13:35
 */
public class FileSelect extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JButton btn = null;

    JTextField textField = null;

    public FileSelect(){
        this.setTitle("浏览");
        FlowLayout layout = new FlowLayout();// 布局
        JLabel label = new JLabel("请选择文件：");// 标签
        textField = new JTextField(30);// 文本域
        btn = new JButton("浏览");// 钮1

        // 设置布局
        layout.setAlignment(FlowLayout.LEFT);// 左对齐
        this.setLayout(layout);
        this.setBounds(400, 200, 600, 70);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btn.addActionListener(this);
        this.add(label);
        this.add(textField);
        this.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showDialog(new JLabel(), "请选择目标文件夹");
        File file = chooser.getCurrentDirectory();
        textField.setText(file.getAbsoluteFile().toString());
    }

    public static void main(String[] args) throws InterruptedException {
        new Windows();
//        new  FileSelect();
    }
}
