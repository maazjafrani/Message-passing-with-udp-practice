import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    //Below is the constructor:
    public Client(DatagramSocket datagramSocket, InetAddress inetAddress){
        this.datagramSocket=datagramSocket;
        this.inetAddress=inetAddress;
    }

    //below will send msg and then will wait for reply/response:
    public void sendThenReceive(){
        Scanner scanner=new Scanner(System.in);// We will write msg and send through console.
        //Now we want to client to continue to run or want client to send and receive msgs continusly:
        while(true){
            try{
                String messageToSend=scanner.nextLine(); //reading string msg from console here.
                //below we convert string message to byte array so that msg could be sent using datagram packet
                buffer=messageToSend.getBytes();  //msg in bytes stored in buffer.
                DatagramPacket datagramPacket=new DatagramPacket(buffer, buffer.length,inetAddress,1234);
                datagramSocket.send(datagramPacket);//sending msg
                //below is working for data/response received by server:
                datagramSocket.receive(datagramPacket); //will receive from server.
                String messageFromServer=new String(datagramPacket.getData(),0,datagramPacket.getLength());
                //printing below on the console the msg sent from server side:
                System.out.println("The Server says you said: " + messageFromServer);
            }catch(IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args)throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket=new DatagramSocket();
        InetAddress inetAddress=InetAddress.getByName("localhost"); //assigning ip address to build connection
        //now creating a client object below and pass Inet address/ip address below:
        Client client=new Client(datagramSocket,inetAddress);
        System.out.println("Send datagram packets to a server.");
        client.sendThenReceive(); //will keep running in a while loop until error occurs.

    }

}
