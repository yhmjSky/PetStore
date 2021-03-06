package xmut.ygnn.petstore.util.http;

import java.io.Serializable;

public class HttpAddress {
    private static String[] args;
    final private static String userAddress="user";
    final private static String storeAddress="store";
    private static final String videoAddress = "video";
    final private static String goodsAddress = "goods";
    final private static String articleAddress="article";
    final private static String useropenidAddress="loginById";


    public static String user(){
        return userAddress;
    }

    public static String store(){
        return storeAddress;
    }

    public static String article(){
        return articleAddress;
    }

    public static String goods(){
        return goodsAddress;
    }


    /**
     *
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “insert”
     * @return
     */
    public static String[] get(String address,String method){
        switch (method){
            case "login":args=getLoginAddress();
                break;
            case "insert":args=getInsertAddress(address);
                break;
            case "update":args=getUpdateAddress(address);
                break;
            case "list":args=getListAddress(address);
                break;
            case "loginById":args=getLoginByOpidAddress();
                break;
        }
        return args;
    }
    /**
     *采用方法重载，分别处理两种情况，带id和不带id
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “delete”
     * @param id id则为相应参数  delete后的id参数
     * @return
     */
    public static String[] get(String address,String method,Serializable id){
        switch (method){
            case "delete":args=getDeleteAddress(address,id);
                break;
            case "line":args=getLineAddress(address,id);
                break;
            case "line2":args=getGoodsByStoreCodeAddress(address,id);
        }
        return args;
    }

    public static String[] get(String address,String method,Serializable id1,Serializable id2){
        switch (method){

            case "line":args=getstoreGoodsAddress(address,id1,id2);
                break;
        }
        return args;
    }

    private static String[] getLoginAddress(){
        args=new String[]{userAddress,"login"};
        return args;
    }

    private static String[] getLoginByOpidAddress(){
        args=new String[]{userAddress,"loginById"};
        return args;
    }


    private static String[] getInsertAddress(String address){
        args=new String[]{address,"insert"};
        return args;
    }
    private static String[] getDeleteAddress(String address, Serializable id){
        args=new String[]{address,"delete", String.valueOf(id)};
        return args;
    }
    private static String[] getUpdateAddress(String address){
        args=new String[]{address,"update"};
        return args;
    }
    private static String[] getLineAddress(String address, Serializable id){
        args=new String[]{address,"line", String.valueOf(id)};
        return args;
    }
    private static String[] getListAddress(String address){
        args=new String[]{address,"list"};
        return args;
    }

    private static String[] getstoreAddress(String address,Serializable id){
        args=new String[]{address,"list", String.valueOf(id)};
        return args;
    }

    private static String[] getstoreGoodsAddress(String address,Serializable id1,  Serializable id2){
        args=new String[]{address,"line", String.valueOf(id1), String.valueOf(id2)};
        return args;
    }

    private static String[] getGoodsByStoreCodeAddress(String address,Serializable id){
        args=new String[]{address,"line2", String.valueOf(id)};
        return args;
    }


    public static String video() {
        return videoAddress;
    }

}

