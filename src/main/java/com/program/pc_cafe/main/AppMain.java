package com.program.pc_cafe.main;

import java.sql.Timestamp;

/**
 * Hello world!
 *
 */
public class AppMain 
{
    public static void main( String[] args )
    {
    	int year = 1995;
    	int month = 9;
    	int date = 13;
    	int hour = 12;
    	int minute = 30; 
    	int second = 11;
    	int nano = 22;
        System.out.println( "Hello World!" );
        Timestamp ts = new Timestamp(year, month, date, hour, minute, second, nano);
        String tss = ts.toString();
        Timestamp rtss = Timestamp.valueOf(tss);
        System.out.println(rtss.getYear());
        System.out.println(rtss.getMonth());
        System.out.println(ts.toString());
        
    }
}
/*
회원
회원아이디 비밀번호 이름 이메일 폰번호 주소

회원시간
회원아이디 잔여시간 충전금액

음식
음식 이름, 음식 가격, 음식 잔여 갯수

주문 
pc번호, 회원아이디, 상품이름 상품갯수, 주문시각  
*/