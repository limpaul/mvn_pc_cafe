package com.program.pc_cafe.image.ui.user;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
import javax.swing.*;
 
public class ChatServer extends JFrame {
	   
	   
    JTextArea ta;
    JScrollPane pane;
    ServerSocket ss;
    Socket s;
    ArrayList<ChatThread> list;
   
    public ChatServer(){
        super("채팅 서버 v1.0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        ta = new JTextArea();
        pane = new JScrollPane(ta);
        add(pane);
        ta.setText("채팅 서버 시작됨!\n");
       
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
       
        //네트워크 코드
        try{
            list = new ArrayList<>();
            ss = new ServerSocket(1995);
            while(true){
               
                s = ss.accept(); //접속되어온소켓을 소켓참조변수 s에 담는다.
                ChatThread t = new ChatThread();
                list.add(t); //멀티 쓰레드를 위해
                t.start();
               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
       
       
    }//생성자
    class ChatThread extends Thread{
       
        BufferedReader br; //한글OK , 한줄씩 입력 OK
        PrintWriter pw; //한글 OK, 한줄씩 출력 OK
        String nickName;
        public ChatThread(){
            try{
          
                //내꺼
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
            }catch(Exception e){
                e.printStackTrace();
            }
           
        }//내부 클래스의 생성자
        
        public void send(String str) { //채팅내용 전송
            pw.println(str);
        }
        @Override
        public void run(){
        	String data="";
            try {
                while((data=br.readLine())!=null){                 
                    broadcast(data);
                    ta.append(data+"\n");//area 에 append
                }//while
            } catch (Exception e) {
                	System.out.println("사용자 종료");
            }
        }
    }
    //inner class ChatThread end
    //외부 클래스의 메소드
    public void broadcast(String str){ //전체 애들한테 보여주도록 설정 
        for (int i = 0; i < list.size(); i++) {
            ChatThread t = (ChatThread)list.get(i);
            t.send(str);
        }
       
    }
    public static void main(String[] args) {
		new ChatServer();
	}
   
   
}