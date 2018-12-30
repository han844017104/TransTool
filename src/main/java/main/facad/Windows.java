/*
 * Created by JFormDesigner on Sun Dec 30 13:25:55 GMT+08:00 2018
 */

package main.facad;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import main.bean.ApiTypeInfo;
import main.bean.RunThread;
import main.biz.Escaper;
import main.biz.impl.Escaper1;
import main.biz.impl.Escaper2;
import main.biz.impl.Fixer1;
import main.filter.Filter;
import main.filter.RexString;
import main.filter.Rule;
import main.http.Transer;
import main.io.FileAppender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/30  13:12
 */
public class Windows extends JFrame {

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JButton OrignBut;
    private static JTextField OrignPath;
    private JLabel label2;
    private JButton OutDirBut;
    private static JTextField OutputDirPath;
    private JLabel label3;
    private JButton FileFilterBut;
    private JTextField FIlterFiles;
    private JPanel buttonBar;
    private static JButton okButton;
    private JScrollPane scrollPane1;
    private JLabel label4;
    private JLabel label5;
    private static JTextArea Consol;
    private static File[] filterFilesArray;
    private static Date startDate;

    public Windows() {
        initComponents();
    }

    private void Start(MouseEvent e) {
        if (!okButton.isEnabled()) {
            return;
        }
        String orignPathText = OrignPath.getText();
        String outputDirPathText = OutputDirPath.getText();
        if (orignPathText == null || orignPathText.length() == 0 || outputDirPathText == null || outputDirPathText.length() == 0) {
            JOptionPane.showMessageDialog(null, "请选择目标文件夹和输出文件夹！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        okButton.setEnabled(false);
        okButton.setText("Translating ...");
        startDate = new Date();
        Consol.append("############################  Translate Start  #############################\n");
        info("Initing Translater ...... ---------------->");
        Translater translater = new Translater();
        translater.setTranser(new Transer(ApiTypeInfo.BaiDu));
        Filter fileFilter = new Filter();
        HashMap<String, String> map = new HashMap<>();
        if (filterFilesArray != null && filterFilesArray.length > 0) {
            for (File file : filterFilesArray) {
                map.put(file.getName(), "-");
            }
        }
        fileFilter.setItem(map);
        translater.setFileFilter(fileFilter);
        Filter textFilter = new Filter();
        ArrayList<String> strings2 = new ArrayList<>();
        strings2.add("");
        textFilter.setItem(strings2);
        textFilter.setOtherRule((Rule) str -> {
            String[] result = RexString.GetRegResult(RexString.shuangyinhao, str);
            if (result == null || result.length == 0) {
                return false;
            }
            for (String s : result) {
                if (s.contains(".png") || s.contains(".jpg") || s.contains(".mp3") || s.contains(".wav") || s.contains(".txt") || s.contains(".ttf")) {
                    return false;
                }
            }
            String cache = str;
            for (String s : result) {
                cache = cache.replace(s, "");
            }
            if (cache.contains("if") || cache.contains("==")) {
                return false;
            }
            return true;
        });
        HashMap<String, String> map2 = new HashMap<>();
        textFilter.setItem(map2);
        translater.setTextFilter(textFilter);
        ArrayList<Escaper> list = new ArrayList<>();
        Escaper1 escaper1 = new Escaper1();
        escaper1.setRex(RexString.zhongkuohao);
        Escaper2 escaper2 = new Escaper2();
        escaper2.setRex(RexString.dakuohao);
        list.add(escaper1);
        list.add(escaper2);
        translater.setEscapers(list);
        translater.setFixer(new Fixer1());
        translater.setInPath(orignPathText);
        translater.setOutPath(outputDirPathText);
        info("Translater inited ! ---------------->");
        info("Start translate ---------------->");
        RunThread runThread = new RunThread();
        runThread.setTranslater(translater);
        Thread thread = new Thread(runThread);
        thread.start();

    }

    public static void end() {
        Consol.append("############################  Translate end  #############################\n");
        Date d2 = new Date();
        long diff = startDate.getTime() - d2.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long sec = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000);
        okButton.setEnabled(true);
        okButton.setText("Start");
        JOptionPane.showMessageDialog(null, "翻译完成！\n" + "本次耗时 ： " + days + "天" + hours + "小时" + minutes + "分" + sec + "秒 ", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void info(String s) {
        Consol.append(getTime() + " [INFO] : " + s + "\n");
        Consol.setCaretPosition(Consol.getDocument().getLength());
    }

    public static void warn(String s) {
        String t = getTime() + " [WARN] : " + s + "\n";
        Consol.append(t);
        Consol.setCaretPosition(Consol.getDocument().getLength());
        FileAppender.append(OutputDirPath.getText() + "\\WarnLogs.log", t);
    }

    public static void error(String s) {
        String t = getTime() + " [ERRO] : " + s + "\n";
        Consol.append(t);
        Consol.setCaretPosition(Consol.getDocument().getLength());
        FileAppender.append(OutputDirPath.getText() + "\\ErrorLogs.log", t);
    }

    private static String getTime() {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int hour = instance.get(Calendar.HOUR);
        int minute = instance.get(Calendar.MINUTE);
        int sec = instance.get(Calendar.SECOND);
        return new StringBuilder().append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":").append(minute).append(":").append(sec).toString();
    }

    private void OrignButMouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showDialog(new JLabel(), "请选择目标文件夹");
        File file = chooser.getSelectedFile();
        if (file != null) {
            OrignPath.setText(file.getAbsoluteFile().toString());
        }
    }

    private void OutDirButMouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showDialog(new JLabel(), "请选择输出文件夹");
        File file = chooser.getSelectedFile();
        if (file != null) {
            OutputDirPath.setText(file.getAbsoluteFile().toString());
        }
    }

    private void FileFilterButMouseClicked(MouseEvent e) {
        String text = OrignPath.getText();
        if (text == null || text.length() == 0) {
            JOptionPane.showMessageDialog(null, "请先选择目标文件夹！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().endsWith(".rpy");
            }

            @Override
            public String getDescription() {
                return "*.rpy";
            }
        };
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(text));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showDialog(new JLabel(), "请选择要忽略的文件");
        File[] files = chooser.getSelectedFiles();
        filterFilesArray = files;
        if (files.length > 0) {
            StringBuilder sb = new StringBuilder("");
            for (File file : files) {
                sb.append(file.getName()).append(", ");
            }
            FIlterFiles.setText(sb.substring(0, sb.length() - 2).toString());
        }
    }

    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        OrignBut = new JButton();
        OrignPath = new JTextField();
        label2 = new JLabel();
        OutDirBut = new JButton();
        OutputDirPath = new JTextField();
        label3 = new JLabel();
        FileFilterBut = new JButton();
        FIlterFiles = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();
        scrollPane1 = new JScrollPane();
        Consol = new JTextArea();

        //======== this ========
        setTitle("TransTool     -- By : Mr.Han");
        setMinimumSize(new Dimension(800, 650));
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL resource = Windows.class.getClassLoader().getResource("./main/ico/bitbug_favicon.ico");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setIconImage(new ImageIcon(resource).getImage());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setAutoscrolls(true);
                contentPanel.setDoubleBuffered(false);
                contentPanel.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));

                //---- label1 ----
                label1.setText("\u76ee\u6807\u76ee\u5f55 :");
                contentPanel.add(label1, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- OrignBut ----
                OrignBut.setText("\u6d4f\u89c8");
                OrignBut.setPreferredSize(new Dimension(50, 30));
                OrignBut.setMaximumSize(new Dimension(50, 30));
                OrignBut.setMinimumSize(new Dimension(80, 30));
                OrignBut.setMargin(new Insets(2, 10, 2, 9));
                OrignBut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        OrignButMouseClicked(e);
                    }
                });
                contentPanel.add(OrignBut, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- OrignPath ----
                OrignPath.setEditable(false);
                OrignPath.setAutoscrolls(false);
                OrignPath.setMinimumSize(new Dimension(450, 35));
                contentPanel.add(OrignPath, new GridConstraints(1, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- label2 ----
                label2.setText("\u8f93\u51fa\u76ee\u5f55 :");
                contentPanel.add(label2, new GridConstraints(2, 0, 1, 1,
                        GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- OutDirBut ----
                OutDirBut.setText("\u6d4f\u89c8");
                OutDirBut.setPreferredSize(new Dimension(50, 30));
                OutDirBut.setMaximumSize(new Dimension(50, 30));
                OutDirBut.setMinimumSize(new Dimension(80, 30));
                OutDirBut.setMargin(new Insets(2, 10, 2, 9));
                OutDirBut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        OutDirButMouseClicked(e);
                    }
                });
                contentPanel.add(OutDirBut, new GridConstraints(3, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- OutputDirPath ----
                OutputDirPath.setMinimumSize(new Dimension(450, 35));
                OutputDirPath.setEditable(false);
                contentPanel.add(OutputDirPath, new GridConstraints(3, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- label3 ----
                label3.setText("\u8fc7\u6ee4\u6587\u4ef6 :");
                contentPanel.add(label3, new GridConstraints(4, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- FileFilterBut ----
                FileFilterBut.setText("\u9009\u62e9\u6587\u4ef6");
                FileFilterBut.setPreferredSize(new Dimension(50, 30));
                FileFilterBut.setMaximumSize(new Dimension(50, 30));
                FileFilterBut.setMinimumSize(new Dimension(80, 30));
                FileFilterBut.setMargin(new Insets(2, 10, 2, 9));
                FileFilterBut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        FileFilterButMouseClicked(e);
                    }
                });
                contentPanel.add(FileFilterBut, new GridConstraints(5, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- FIlterFiles ----
                FIlterFiles.setMinimumSize(new Dimension(450, 35));
                FIlterFiles.setEditable(false);
                contentPanel.add(FIlterFiles, new GridConstraints(5, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                contentPanel.add(label4, new GridConstraints(6, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- label5 ----
                label5.setText("Console");
                contentPanel.add(label5, new GridConstraints(6, 0, 1, 3,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                //---- okButton ----
                okButton.setText("Start");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Start(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            {

                //---- Consol ----
                Consol.setEditable(false);
                scrollPane1.setViewportView(Consol);
            }
            dialogPane.add(scrollPane1, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }

}
