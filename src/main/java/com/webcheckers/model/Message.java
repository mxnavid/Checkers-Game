package com.webcheckers.model;


public class Message{
    public enum Type{
        error,
        info
    }

    private Type type;
    private String text;


    public Message(Type type, String messageText){
        this.type = type;
        this.text = messageText;
    }

    /**
     * retrieves the type of message
     * @return info or error enum
     */
    public Type getType() {
        return this.type;

    }

    /**
     * Two objects are equal if they have the same text and type
     * @param obj the other object to compare to
     * @return true if object equals; false otherwise
     */
    @Override
    public boolean equals(Object obj){

        if(obj instanceof Message){
            Message that = (Message) obj;
            return this.text.equals(that.text) && this.type == that.type;
        }

        return false;

    }

    /**
     * Hash code of a Message based on the text
     * @return hash code
     */
    @Override
    public int hashCode(){
        return this.text.hashCode();
    }

}
