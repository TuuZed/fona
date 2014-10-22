/**
 * Fona / Sim800 Library for Java Copyright 2014 Andrew Bythell
 * <abythell@ieee.org>
 */

package com.angryelectron.fona;

import java.util.HashMap;

/**
 * Compose e-mail messages.  Send the message via Fona.emailSend().
 */
public class FonaEmailMessage {
    String fromAddress;
    String fromName;
    final HashMap<String, String> to = new HashMap<>();
    final HashMap<String, String> cc = new HashMap<>();
    final HashMap<String, String> bcc = new HashMap<>();    
    String subject;
    String body;
    
    /**
     * Set the name and e-mail address of the sender.
     * @param address Sender's e-mail address.
     * @param name Sender's name.
     */
    public void from(String address, String name) {
        this.fromAddress = address;
        this.fromName = name;
    }
    
    /**
     * Set the name(s) and address(es) of the recipient(s).  A new recipient will
     * be added each time this method is called.
     * @param address Recipient's e-mail address.
     * @param name Recipient's name.
     */
    public void to(String address, String name) {
        to.put(address, name);
    }
    
    /**
     * Set the name(s) and address(es) of the carbon-copy (CC) recipient(s).  A 
     * new CC will be added each time this method is called.
     * @param address CC recipient's e-mail address.
     * @param name CC recipient's name.
     */
    public void cc(String address, String name) {
        cc.put(address, name);
    }
    
    /**
     * Set the name(s) and address(es) of the blind carbon-copy (BCC) recipient(s).  A 
     * new BCC will be added each time this method is called.
     * @param address BCC recipient's e-mail address.
     * @param name BCC recipient's name.
     */
    public void bcc(String address, String name) {
        bcc.put(address, name);
    }
    
    /**
     * Set the subject of the message.
     * @param subject E-mail subject.
     */
    public void subject(String subject) {
        this.subject = subject;
    }
    
    /**
     * Set the body of the message.
     * @param body E-mail body.
     */
    public void body(String body) {
        this.body = body;        
    }
                
}
