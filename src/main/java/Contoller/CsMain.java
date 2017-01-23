package Contoller;

import DBConnect.DBConnect;

/**
 * Created by TB690275 on 2017/1/23.
 */
public class CsMain {

    private static DBConnect CsConnect = new DBConnect();

    public static void main(String[] args){

        String cql = "SELECT * FROM login.user ;";

        CsConnect.CsConnect("192.168.56.130","login",cql);

        //System.out.print();


    }


}
