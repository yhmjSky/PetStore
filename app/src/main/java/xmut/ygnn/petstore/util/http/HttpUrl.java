package xmut.ygnn.petstore.util.http;

public class HttpUrl {
    //通过cmd命令行输入 ipconfig  IpV4地址即为本地的ip地址
    final private static String baseUrl="http://192.168.179.1:8081";

    public static String getBaseUrl() {
        return baseUrl;
    }

}
