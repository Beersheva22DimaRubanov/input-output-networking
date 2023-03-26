package telran.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class UdpUtils {
	public static final int MAX_BUFFER_LENGTH = 100000;
    public static byte[] toBytesArray(Serializable obj) throws Exception{
   	 try (ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
   			 ObjectOutputStream objectOutput = new ObjectOutputStream(bytesOutput)) {
   		 objectOutput.writeObject(obj);
   		 return bytesOutput.toByteArray();
   	 }
    }
    public static Serializable toSerializable(byte [] array, int length) throws Exception {
   	 try (ByteArrayInputStream bytesInput =
   			 new ByteArrayInputStream(array, 0, length);
   			 ObjectInputStream objectInput = new ObjectInputStream(bytesInput)) {
   		return (Serializable) objectInput.readObject(); 
   	 }
    }
}
