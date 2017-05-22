package com.angryelectron.fona;

import org.junit.Test;


public class NewFonaTest {
    @Test
    public void GPRSTest() {
        Fona fona = new Fona();
        try {
            fona.open("COM6", 9600);
            fona.gprsEnable(null, null, null);
            long start = System.currentTimeMillis();
            String response = fona.gprsHttpGet("http://baidu.com");
            System.out.println(response);
            System.out.println(System.currentTimeMillis() - start);
        } catch (FonaException e) {
            e.printStackTrace();
        } finally {
            try {
                fona.gprsDisable();
            } catch (FonaException e) {
                e.printStackTrace();
            }
            try {
                fona.close();
            } catch (FonaException e) {
                e.printStackTrace();
            }
        }
    }
}