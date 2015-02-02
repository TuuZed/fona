/**
 * Fona / Sim800 Library for Java Copyright 2014 Andrew Bythell
 * <abythell@ieee.org>
 */
package com.angryelectron.fona;

import com.angryelectron.fona.Fona.Mode;
import com.angryelectron.fona.Fona.Ready;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;

public class FonaTest {

    //TODO: move these to a properties file so others can test.
    private static final String PORT = "/dev/ttyUSB2";
    private static final Integer BAUD = 115200;

    //Credentials for Rogers Wireless required for testing GPRS.
    private static final String APN = "internet.com";
    private static final String USER = "wapuser1";
    private static final String PWD = "wap";
    private static final String SMTP = "smtp.rogerswirelessdata.com";

    //E-Mail Settings.  Use an address of an account you can access
    //to verify the message.
    private static final String TO_ADDRESS = "";
    private static final String TO_NAME = "";
    private static final String POPSERVER = "";
    private static final Integer POPPORT = 110;
    private static final String POPUSER = "";
    private static final String POPPWD = "";

    //SMS Settings.
    private static final String SMSNUMBER = "";

    private static final Fona fona = new Fona();

    public FonaTest() {           
    }

    @Before
    public void setUp() throws FonaException {        
        try {
            fona.open(PORT, BAUD);
        } catch (FonaException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }        
    }

    @After
    public void tearDown() throws FonaException {
        fona.close();
    }

    /**
     * Test of gpioSetOutput method, of class Fona. Can't really test without
     * external hardware to monitor pin.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testGpioOutput() throws FonaException {
        System.out.println("gpioOutput");
        fona.gpioSetOutput(1, 1);
        fona.gpioSetOutput(1, 0);
    }

    /**
     * Can't really test this without external hardware. Return value is
     * irrelevant - just ensure no exception is thrown.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testGpioInput() throws FonaException {
        int pin = 1;
        System.out.println("gpioInput");
        fona.gpioSetInput(pin);
        fona.gpioGetInput(pin);
    }

    @Test(expected = FonaException.class)
    public void testGpioBadDirection() throws FonaException {
        int pin = 1;
        int value = 1;
        System.out.println("gpioBadDirection");
        fona.gpioSetOutput(pin, value); //pin is an output
        fona.gpioGetInput(pin); //should throw exception.  can't read output pins.
    }

    /**
     * Test of gprsEnable method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testGprs() throws FonaException {
        System.out.println("gprsEnable");
        fona.gprsDisable();
        assertFalse(fona.gprsIsEnabled());
        fona.gprsEnable(APN, USER, PWD);
        assertTrue(fona.gprsIsEnabled());
        fona.gprsDisable();
        assertFalse(fona.gprsIsEnabled());
    }

    /**
     * Test of gprsHttpGet method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testGprsHttpGet() throws FonaException {
        System.out.println("gprsHttpGet");
        if (!fona.gprsIsEnabled()) {
            fona.gprsEnable(APN, USER, PWD);
        }
        assertTrue("GPRS not enabled", fona.gprsIsEnabled());

        String response = fona.gprsHttpGet("http://httpbin.org/user-agent");
        if (!response.contains("SIMCOM_MODULE")) {
            fail("Unexpected response: " + response);
        }
    }

    /**
     * Test of batteryVoltage method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testBatteryVoltage() throws FonaException {
        System.out.println("batteryVoltage: " + fona.batteryVoltage());
    }

    /**
     * Test of batteryPercent method, of class Fona.
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testBatteryPercent() throws FonaException {
        System.out.println("batteryPercent: " + fona.batteryPercent());
    }

    @Test
    public void testBatteryCharge() throws FonaException {
        System.out.println("batteryChargingState: " + fona.batteryChargingState());
    }

    /**
     * Test of time method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testTime() throws FonaException {
        System.out.print("testTime: ");
        if (!fona.gprsIsEnabled()) {
            fona.gprsEnable(APN, USER, PWD);
        }
        Date date = fona.gprsTime();
        System.out.println(date);
        assertNotNull(date);
        fona.gprsDisable();
    }

    /**
     * Test of smsSend method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testSmsSend() throws FonaException {
        assumeTrue(!SMSNUMBER.isEmpty());                
        fona.smsSend(SMSNUMBER, "Test SMS from FONA");
    }

    /**
     * Test of simReadADC method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testSimReadADC() throws FonaException {
        System.out.println("simReadADC: " + fona.simReadADC());
        Integer value = fona.simReadADC();
        assertTrue(0 <= value && value <= 2800);
    }

    /**
     * Test of simRSSI method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testSimRSSI() throws FonaException {
        System.out.println("simRSSI: " + fona.simRSSI() + "dBm");
    }

    /**
     * Test of simProvider method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testSimProvider() throws FonaException {
        System.out.println("simProvider: " + fona.simProvider());
    }

    /**
     * Test of temperature method, of class Fona.
     *
     * @throws com.angryelectron.fona.FonaException
     */
    @Test
    public void testTemperature() throws FonaException {
        System.out.println("temperature: " + fona.temperature() + "C");
    }

    
    /**
     * TODO: improve the e-mail test methods so that they Send, Then 
     * Receive, then Delete the same message on the same server.     
     */
    
    @Test
    public void testEmailSend() throws FonaException {        
        assumeTrue(!TO_ADDRESS.isEmpty());
        System.out.println("emailSend");

        if (!fona.gprsIsEnabled()) {
            fona.gprsEnable(APN, USER, PWD);
        }
        fona.emailSMTPLogin(SMTP, 25);

        FonaEmailMessage email = new FonaEmailMessage();
        email.from("fona@test.com", "Fona");
        email.to(TO_ADDRESS, TO_NAME);
        email.subject("Fona Java Library Email Test");
        email.body("Hello, from FONA.");

        fona.emailSMTPSend(email);
    }

    @Test
    public void testEmailReceive() throws FonaException {        
        assumeTrue(!POPSERVER.isEmpty());
        System.out.println("emailReceive");
        if (!fona.gprsIsEnabled()) {
            fona.gprsEnable(APN, USER, PWD);
        }
        fona.emailPOP3Login(POPSERVER, POPPORT, POPUSER, POPPWD);
        try {
            List<FonaEmailMessage> messages = fona.emailPOP3Get(false);
            assertFalse("No messages.", messages.isEmpty());
            FonaEmailMessage email = messages.get(1);
            assertNotNull("No subject.", email.subject);
            assertFalse("No subject", email.subject.isEmpty());
            assertFalse("No body.", email.body.isEmpty());
            assertFalse("No to address.", email.to.isEmpty());
            assertFalse("No from address.", email.from.isEmpty());
        } finally {
            fona.emailPOP3Logout();
        }
    }
    
    @Test
    public void testEmailDelete() throws FonaException {        
        assumeTrue(!POPSERVER.isEmpty());
        System.out.println("emailDelete");
        fona.emailPOP3Login(POPSERVER, POPPORT, POPUSER, POPPWD);
        try {
            fona.emailPOP3Delete(1);
        } finally {
            fona.emailPOP3Logout();
        }
    }

    @Test
    public void testSmsList() throws FonaException {
        System.out.println("smsList");
        List<FonaSmsMessage> messages = fona.smsRead(FonaSmsMessage.Folder.ALL, false);
    }

    @Test
    public void testSmsRead() throws FonaException {
        System.out.println("smsRead");
        FonaSmsMessage message = fona.smsRead(1, false);
        assertNotNull("No folder.", message.folder);
        assertNotNull("No id.", message.id);
        assertNotNull("No message.", message.message);
        assertNotNull("No sender.", message.sender);
        assertNotNull("No timestamp.", message.timestamp);
    }
    
    @Test
    public void testResetAndReady() throws FonaException {
        System.out.println("resetReady");
        fona.simReset();
        fona.simWaitForReady(15000, Ready.BOTH);
    }
    
    @Test
    public void testSimFunctionality() throws FonaException {
        System.out.println("simFunctionality");
        fona.simFunctionality(Mode.MIN, false);
        fona.simFunctionality(Mode.FLIGHT, false);
        fona.simFunctionality(Mode.FULL, true); 
        fona.simWaitForReady(15000, Ready.BOTH);
    }
    
    @Test
    public void testSleepAndWake() throws FonaException {
        System.out.println("sleepAndWake");
        fona.simFunctionality(Mode.MIN, false);
        assertFalse(fona.gprsIsEnabled());
        fona.simFunctionality(Mode.FULL, false);
        fona.simWaitForReady(15000, Ready.NETWORK);
        fona.gprsEnable(APN, USER, PWD);
        assertTrue(fona.gprsIsEnabled());
    }
}
