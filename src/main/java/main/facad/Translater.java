package main.facad;

import main.biz.Escaper;
import main.biz.Fixer;
import main.filter.Filter;
import main.filter.RexString;
import main.http.Transer;
import main.io.FileAppender;
import main.io.FileFinder;
import main.io.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  16:39
 */
public class Translater {

    private Filter fileFilter;

    private Filter textFilter;

    private ArrayList<Escaper> escapers;

    private Transer transer;

    private Fixer fixer;

    private String inPath;

    private String outPath;

    public Filter getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(Filter fileFilter) {
        this.fileFilter = fileFilter;
    }

    public Filter getTextFilter() {
        return textFilter;
    }

    public void setTextFilter(Filter textFilter) {
        this.textFilter = textFilter;
    }

    public ArrayList<Escaper> getEscapers() {
        return escapers;
    }

    public void setEscapers(ArrayList<Escaper> escapers) {
        this.escapers = escapers;
    }

    public Transer getTranser() {
        return transer;
    }

    public void setTranser(Transer transer) {
        this.transer = transer;
    }

    public String getInPath() {
        return inPath;
    }

    public Fixer getFixer() {
        return fixer;
    }

    public void setFixer(Fixer fixer) {
        this.fixer = fixer;
    }

    public void setInPath(String inPath) {
        this.inPath = inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public void start() throws IOException {
        FileFinder.setFilter(this.fileFilter);
        File outDir = new File(this.outPath);
        if (!outDir.exists() || !outDir.isDirectory()) {
            outDir.mkdirs();
        }
        List<String> files = FileFinder.findFiles(this.inPath);
        for (String file : files) {
            Windows.info("Find file ：" + file);
        }
        for (String thisFile : files) {
            String fileName = thisFile.substring(thisFile.lastIndexOf("\\") + 1);
            if (!this.fileFilter.check(thisFile)) {
                continue;
            }
            Windows.info("Strat translate file : " + thisFile);
            File file = new File(this.outPath + fileName);
            if (file.exists()) file.delete();
            ArrayList<String> lines = FileReader.readLine(thisFile);
            int lineNum = 0;
            for (String line : lines) {
                lineNum++;
                try {
                    if (this.textFilter.check(line)) {
                        Windows.info("Translating  doing line[" + lineNum + "] text : " + line);
                        String[] strings = RexString.GetRegResult(RexString.shuangyinhao, line);
                        StringBuilder cn = new StringBuilder(line);
                        for (String string : strings) {
                            int start = cn.indexOf(string);
                            int end = start + string.length();
                            for (Escaper escaper : this.escapers) {
                                string = escaper.escaper(string);
                            }
                            String ret = this.transer.trans(string.substring(1, string.length() - 1));
                            for (Escaper escaper : this.escapers) {
                                ret = escaper.unEscaper(ret);
                            }
                            if (this.fixer != null) {
                                ret = fixer.fix(ret);
                            }
                            cn.replace(start, end, ret);
                        }
                        Windows.info("Translate complate line[" + lineNum + "] text : " + cn);
                        FileAppender.append(this.outPath + "\\" + fileName, cn.toString());
                    } else {
                        FileAppender.append(this.outPath + "\\" + fileName, line);
                    }
                } catch (Exception e) {
                    Windows.error("Line[" + lineNum + "]  error : ");
                    e.printStackTrace();
                    FileAppender.append(this.outPath + "\\" + fileName, line);
                }
            }
        }
    }
}
