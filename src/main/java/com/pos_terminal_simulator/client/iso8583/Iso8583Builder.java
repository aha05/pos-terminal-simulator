package com.pos_terminal_simulator.client.iso8583;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Iso8583Builder {

    /**
     * Builds the ISO 8583 message as ASCII bytes.
     */
    public byte[] build(Iso8583Message message) {

        String rawMessage = buildString(message);

        return rawMessage.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * Builds the ISO 8583 message as a raw String.
     *
     * Example:
     *
     * 02003238448008C000000000000000000010000...
     */
    public String buildString(Iso8583Message message) {

        validateMessage(message);

        StringBuilder output = new StringBuilder();

        // ---------------------------------------------------------
        // MTI
        // ---------------------------------------------------------

        output.append(message.getMti());

        // ---------------------------------------------------------
        // Bitmap
        // ---------------------------------------------------------

        Bitmap bitmap =
                Bitmap.fromFields(
                        message.getFields().keySet()
                );

        /*
         * The bitmap is binary data in a real ISO 8583 message.
         *
         * However, your simulator message is currently using
         * the hexadecimal representation:
         *
         * 3238448008C00000
         *
         * Therefore convert bitmap bytes to HEX.
         */
        output.append(bytesToHex(bitmap.toBytes()));

        // ---------------------------------------------------------
        // Data Elements
        // ---------------------------------------------------------

        for (Map.Entry<Integer, String> entry :
                message.getFields().entrySet()) {

            IsoField field =
                    IsoField.fromNumber(entry.getKey());

            String value = entry.getValue();

            writeField(output, field, value);
        }

        return output.toString();
    }

    private void writeField(
            StringBuilder output,
            IsoField field,
            String value
    ) {

        validateFieldLength(field, value);

        switch (field.getLengthType()) {

            case FIXED -> output.append(value);

            case LLVAR -> {

                String length =
                        String.format("%02d", value.length());

                output.append(length);
                output.append(value);
            }

            case LLLVAR -> {

                String length =
                        String.format("%03d", value.length());

                output.append(length);
                output.append(value);
            }
        }
    }

    private void validateFieldLength(
            IsoField field,
            String value
    ) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "DE" + field.getNumber() + " cannot be null"
            );
        }

        int length = value.length();

        switch (field.getLengthType()) {

            case FIXED -> {

                if (length != field.getMaxLength()) {

                    throw new IllegalArgumentException(
                            "DE"
                                    + field.getNumber()
                                    + " must be exactly "
                                    + field.getMaxLength()
                                    + " characters, but was "
                                    + length
                    );
                }
            }

            case LLVAR, LLLVAR -> {

                if (length > field.getMaxLength()) {

                    throw new IllegalArgumentException(
                            "DE"
                                    + field.getNumber()
                                    + " exceeds maximum length "
                                    + field.getMaxLength()
                    );
                }
            }
        }
    }

    private void validateMessage(
            Iso8583Message message
    ) {

        if (message == null) {

            throw new IllegalArgumentException(
                    "ISO message cannot be null"
            );
        }

        if (message.getMti() == null) {

            throw new IllegalArgumentException(
                    "MTI is required"
            );
        }
    }

    /**
     * Converts bitmap bytes to hexadecimal.
     *
     * Example:
     *
     * [0x32, 0x38, 0x44, 0x80, ...]
     *
     * becomes:
     *
     * 3238448008C00000
     */
    private String bytesToHex(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {

            result.append(
                    String.format(
                            "%02X",
                            b & 0xFF
                    )
            );
        }

        return result.toString();
    }
}

