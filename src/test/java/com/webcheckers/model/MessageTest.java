package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class MessageTest {

    private Message m1 = new Message(Message.Type.error,"This is an error message.");
    private Message m2 = new Message(Message.Type.info,"This is an info message.");

    /**
     * Test the message text of a Message data type
     */
    @Test
    public void testMessageText(){

        assertEquals(m1.toString(),"This is an error message.");
        assertEquals(m2.toString(), "This is an info message.");

    }


    /**
     * Test the message type
     */
    @Test
    public void testMessageType(){

        assertNotEquals(m1.getType(), Message.Type.info);
        assertEquals(m2.getType(), Message.Type.info);

    }

    /**
     * Test the equals method works
     */
    @Test
    public void testMessageEquals(){

        Message newM1 = new Message(Message.Type.error, "This is an error message.");
        assertTrue(newM1.equals(m1));

    }



}