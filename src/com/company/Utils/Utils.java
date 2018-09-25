package com.company.Utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Utils
{

    /**
     * @param values the values that need to be converted to a byte array
     * @return the byte array that came from the passed array list
     */
    public static byte[] floatArrayListToByteArray(ArrayList<Float> values)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4 * values.size());

        for (float value : values){
            buffer.putFloat(value);
        }

        return buffer.array();
    }

    /**
     * @param values the values that need to be converted to a byte array
     * @return the byte array that came from the passed array
     */
    public static byte[] floatArrayToByteArray(float[] values)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4 * values.length);

        for (float value : values){
            buffer.putFloat(value);
        }

        return buffer.array();
    }

    /***
     * @param buffer the byte array that needs to be converted to a float
     * @return an array of floats form the input bytes
     */
    public static float[] floatArrayFromByteArray(byte[] buffer)
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        float[] floatArray = new float[buffer.length / 4];  // 4 bytes per float
        for (int i = 0; i < floatArray.length; i++)
        {
            try
            {
                floatArray[i] = dataInputStream.readFloat();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return  floatArray;
    }

    public static float[] floatArrayFromByteArray(byte[] buffer, int outPutArraySize)
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        float[] floatArray = new float[outPutArraySize];

        for (int i = 0; i < floatArray.length; i++)
        {
            try
            {
                floatArray[i] = dataInputStream.readFloat();
            } catch (IOException e)
            {
                floatArray[i] = -1;
            }
        }

        return  floatArray;
    }

    public static float[] floatArrayFromByteArray(byte[] buffer, int outPutArraySize, float defualtVal)
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        float[] floatArray = new float[outPutArraySize];

        for (int i = 0; i < floatArray.length; i++)
        {
            try
            {
                floatArray[i] = dataInputStream.readFloat();
            } catch (IOException e)
            {
                floatArray[i] = defualtVal;
            }
        }

        return  floatArray;
    }

    public static float[] generateFloatArray(int length, float values)
    {
        float[] array = new float[length];

        for(int i = 0; i < array.length; i++)
            array[i] = values;


        return array;
    }

    /***
     * Converts an int to binary in form of a byte array
     * @param value input value to be converted to binary
     * @return a byte array that is the int in binary
     */
    public static byte[] intToBinary(int value)
    {
        String tmp = Integer.toBinaryString(value);
        int len = tmp.length();
        int change = 8 - (len % 8);

        if(change != 0) len += change;

        byte[] res = new byte[len];
        for(int i = 0; i < len; i++) {
            if(i < change){
                // Tag on leading zeroes if needed
                res[i] = 0x00;
            }else{
                char c = tmp.charAt(i - change);
                res[i] = (byte) (c == '0' ? 0x00 : 0x01);
            }
        }

        return res;
    }

    /**
     * Removes the zeros for an int array
     * @param input the array that the zeros are to be removed from
     * @return a new array that has the zeros removed from it
     */
    public static int[] removeZeros(int[] input)
    {
        int targetIndex = 0;
        for(int sourceIndex = 0;  sourceIndex < input.length;  sourceIndex++ )
        {
            if( input[sourceIndex] != 0 )
                input[targetIndex++] = input[sourceIndex];
        }

        int[] newArray = new int[targetIndex];
        System.arraycopy( input, 0, newArray, 0, targetIndex );

        return newArray;
    }

    /**
     * Converts any value that is a subclass of {@link Number} to an array of {@code byte}s that equal its integral binary form, padded
     * to the specified spacing. Non-integral subclasses of {@link Number} (for example, {@link Float} will have their non-integral
     * components removed with no rounding (effectively rounded down for most types).
     * @param value the value to convert to a binary array
     * @param padding the multiple to which the result should be padded. For example, for the input {@code 3400} (result spacing added for clarity):
     *                <ul>
     *                <li>If the padding is {@code 8}: result will be {@code 0000 1101 0100 1000}</li>
     *                <li>If the padding is {@code 10}: result will be {@code 00 0000 1101 0100 1000}</li>
     *                <li>If the padding is {@code 0}: result will be {@code 1101 0100 1000}</li>
     *                </ul>
     * @return the integral binary form of the input value, stored in a {@code byte} array
     */
    public static byte[] toBinaryArray(Number value, int padding)
    {
        String tmp = Integer.toBinaryString(value.intValue());
        int len = tmp.length();
        int change = padding - (len % padding);
        if(change != 0) len += change;

        byte[] res = new byte[len];
        for(int i = 0; i < len; i++) {
            if(i < change){
                // Tag on leading zeroes if needed
                res[i] = 0x00;
            }else{
                char c = tmp.charAt(i - change);
                res[i] = (byte) (c == '0' ? 0x00 : 0x01);
                res[i] = Byte.parseByte(c + "", 2);
            }
        }

        return res;
    }

    public static byte[] capValues(byte[] input, float max, float min)
    {
        float[] fInputs = floatArrayFromByteArray(input);

        for(int i = 0; i < fInputs.length; i++)
        {
            if(fInputs[i] > max)
                fInputs[i] = max;

            if(fInputs[i] < min)
                fInputs[i] = min;

            if(Float.isNaN(fInputs[i]))
                fInputs[i] = 0;
        }

        return floatArrayToByteArray(fInputs);
    }


}
