import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class Ipv4Client {
	public static void main(String[] args ){
		try (Socket socket = new Socket("18.221.102.182", 38003)) {
			System.out.println("Connected to server.");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			OutputStream os = socket.getOutputStream();
			
			for ( int i=0, dataLength=2; i < 12; i++,dataLength*=2){
				System.out.println("data length: " + dataLength);
				Packet p = new Packet(dataLength);
				byte[] packetBytes = p.getBytes();
				os.write(packetBytes);
				String result = br.readLine();
				System.out.println(result);
			}

		} catch ( Exception e ){
			e.printStackTrace();
		} finally {
			System.out.println("Disconnected from server.");
		}

	}

}
