import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
private DatagramSocket datagramSocket;
private byte[] buffer= new byte[256];//are bytes used as buffer to store messages sent from client

    public Server(DatagramSocket datagramSocket){ //Constructor
        this.datagramSocket=datagramSocket;
    }

    public void receiveThenSend(){  //This method will receive the message and then send reply
        while(true){  //loop will keep running infinitely to keep the server running
            try{
                //Datagram Packet is used for udp as udp is a connectionless protocol
                //below we are creating a datagram packet:
                //will read the buffer if any value received:
                DatagramPacket datagramPacket=new DatagramPacket(buffer, buffer.length);//buffer will store the message and buffer.length is the message length
                //datagram socket will wait for data to be received from client:
                datagramSocket.receive(datagramPacket);
                //inet address is the ip address and we are getting the address below which would be in the header:
                InetAddress inetAddress=datagramPacket.getAddress();
                //We would also need port number along with IP address using which the client sent the message
                //to give reply/response to the client:
                int port= datagramPacket.getPort();
                //below we will store the message received from the client side to a variable below:
                //offset=0 as to convert data to string:
                String messageFromClient=new String(datagramPacket.getData(),0,datagramPacket.getLength());
                //below printing the message received to the terminal:
                System.out.println("Message from Client: "+ messageFromClient);
                //below creating a new datagram packet with same portnumber and ip address and message as client to send him the
                //the same message as was received by the server from the client:
                datagramPacket=new DatagramPacket(buffer, buffer.length, inetAddress, port); //buffer has the value/msg that client sent to server
                datagramSocket.send(datagramPacket); //sending packet recieved back to client as a reply/response!!!



            }catch(IOException e){
                e.printStackTrace();
                break;


            }

        }

    }

    //Main method to run the project and to instantiate server object:
    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket=new DatagramSocket(1234); //using port 1244
        Server server=new Server(datagramSocket); //creating a server
        server.receiveThenSend(); //calling the method receive then send.

    }




}
