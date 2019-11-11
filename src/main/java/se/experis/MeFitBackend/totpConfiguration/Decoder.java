package se.experis.MeFitBackend.totpConfiguration;


public interface Decoder {
    /**
     * Return the byte representation of the given string
     * @param input
     * @return The byte representation of the given string
     */
    byte[] decode(String input);
}
