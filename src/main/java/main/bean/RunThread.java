package main.bean;

import main.facad.Translater;
import main.facad.Windows;

import java.io.IOException;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/30  16:52
 */
public class RunThread implements Runnable {

    private Translater translater;

    @Override
    public void run() {
        try {
            translater.start();
            Windows.end();
        } catch (IOException e) {
            Windows.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public Translater getTranslater() {
        return translater;
    }

    public void setTranslater(Translater translater) {
        this.translater = translater;
    }
}
