import java.util.Arrays;

public class Packet {
	public static final int IP_VERSION = 4;
	public static final int IHL = 5;
	public static final int TTL = 50;
	public static final int PROTOCOL_NUM = 6;
	public static final int[] DEST_IP = {18,221,102,182};
	byte[] bytes;
	
	public Packet( int dataSize ){
		bytes = new byte[5 * 4 + dataSize];
		//Version, IHL 
		bytes[0] = (IP_VERSION << 4) | IHL;
		//Total Length
		bytes[2] = (byte) (bytes.length >>> 8);
		bytes[3] = (byte) (bytes.length & 0xFF);
		//Flags - Do not fragment
		bytes[6] = 0b01000000;
		//TTL 
		bytes[8] = TTL;
		//Protocol
		bytes[9] = PROTOCOL_NUM;
		//Dest IP 
		bytes[16] = (byte)DEST_IP[0];
		bytes[17] = (byte)DEST_IP[1];
		bytes[18] = (byte)DEST_IP[2];
		bytes[19] = (byte)DEST_IP[3];
		//Header checksum
		byte[] checksumBytes = checksumBytes(Arrays.copyOf(bytes, 20));
		bytes[10] = checksumBytes[0];
		bytes[11] = checksumBytes[1];
}
	
	public byte[] getBytes(){
		return bytes;
	}
	
	public static byte[] checksumBytes(byte[] bytes){
		long sum = 0;
		for ( int i = 0; i < bytes.length; i=i+2){
			int a = byteToUnsignedInt(bytes[i]);
			int b = 0;
			if ( i+1 < bytes.length ){
				b = byteToUnsignedInt(bytes[i+1]);
			}
			//			sum += a * 0x100 + b;
			sum += (a << 8) + b;
			if ( (sum & 0xFFFF0000) != 0 ){
				sum = sum & 0xFFFF;
				sum++;
			}
		}
		short checksum = (short) ~(sum & 0xFFFF);
		byte[] checksumBytes = new byte[2];
		checksumBytes[0] = (byte)(checksum >>> 8);
		checksumBytes[1] = (byte)(checksum & 0xFF);
		return checksumBytes;
	}

	private static int byteToUnsignedInt(byte b){
		return b & 0xFF;
	}
	
	
	
	
}
