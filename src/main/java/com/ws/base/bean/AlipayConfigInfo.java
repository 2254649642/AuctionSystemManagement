package com.ws.base.bean;

public class AlipayConfigInfo {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "9021000137615201";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCUEHAssJmDKfWJWs0lL4zdmm15P8VMhW3RagAWlL1eLPsSl7CFaA9A0u8Y76s8ExXmyanA+pCPa73pZaRV7EWJnFqg8Kl/oni7Ocqta4IuRMS/0zsMc/1ywQx5Ybc8EudOOg1h4ChNm/DBXHN+7U1Pk5s2lfhLOAZonCit4IiXK12V2EsI3n1CPXm71gqQd5kc6qUAiy8o9H7G4X3cBsRueUtpzemw87ikKUUg6TYQL5y9VaSy8xlq8unBZHUZdtWVBfS0d0JHvVGWvDxwgONW2vaBn+aPfDMiK8hC7gzlUHGN+TqwdwN7Ulc6MTwHFRUu4yKT2iRVIlY/4b2E8aXTAgMBAAECggEAQIWVTHQ8wWK0hAGUqiK5gQw9rnuyr7DpdTt7BFwW1YZGdwixPMFm/DmvRa+TcB9lCOhSWC0Yd9dcr+vCHEtU1i3TdJTLWDpl4DjD8KSL2zvOfUVAUyHsjWhA1lhVsBEQyQ8VK9Sx23gcBzcRgXtJmGbbWYMq6yRj7ow2Uecpvq3VE14fTHfBJFsM59VbLJtDgSb3Ib5E9YdkFpmU6FHJ6rBp/NP0kpJM/4XBeQtWWdzC7lGzqifiLgYh+SmuQI6eHg9rUsqxKtr4jvop7Cd3YQn5Q8DObeUvlUEK1/i9qNmWeNVoDBi0Z/Lq8yBGKTL9EmTp168mdIgvSJ7hLpPmmQKBgQDc/h9JmZuA8BvmAMg0eKGWh1doV9PkTYbM4yu94Kax8GBfN2SQAo1KKPaw2drX0FeSDiajYQco9AMEpAlfnwa80uw0JVNgZ9P2cGc/9fT+8QpIpz5bvfQ/uMijg/tiv4jKhF019d8g/VEMuhcaksDvJ9GqMm8rUqYHxxLRQKtBHwKBgQCrhNxhUO+yUqY7Qg6W1dAcYO9WpeOF4JRgplZMO2OjQ6QLlhl8wt/zNhaO38aZgghHQK4Rjg422ZdJb5yUUwQMfVDXQ0dO2lNnfAnOklwsLRRYST7y7YmonOpGAHgBhbotRvEIfTzVes+dLtntvMhWD8yZNb7tZTVOHXb2QeGAzQKBgB7mueFExWXGLWCAA36yz4WI70P74/ksjgjgSyGF5ElFX3Rw/nLUqIUSLUyVafB7hYM6z1RRIPstA68GcPkZ94s3wyeXgI0ed5kiJo3fCp8LfXCjUijp6tluMUAMGH66YZpP3+jHtKecRFUPQGPK2YeXfc3at4jLScQUpiD0df3pAoGBAKnrf+CxLCX/l0uRj56CyB+1MKcoxRT2fkiZwVjYZUdzVWtdEnb1IKRz6zk0HC4sEkN0on4lu5M/UY+hEQAolhRBefsoXdaSmJom2hhaCxKAvm0Qc1p7VStD+RvMjxcDAoSkiVmdqMzdVB0mMyRBKD+Ke2k1Q6cv4vU2X4prjhnpAoGBAIUqYHLVl51Dp/EuioJhmq90JxcdA+GeRfycH9dOUmBRqxEG+r5l1/CQ2LMyIR1ta2sGqxRx+aqViLVwwaiZI5aPuMJEHYDfHXpnn2c4waAji/wbt0RVreSJxaqIQoknqqn5IcGQevfyWJ3RUrxi56Nbiq/06LPAg4qP3L0ayg8W";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjSwevxayuVqLJQY14igCrCoBf81TE69PRdZVmQxSMMCdhua6Ng2cHUxpYgtW80QZUXzjt+rmwVVHHBcHMjRS6f5JrV3KVjiQ+ozkPQr7jzxaGPuiROaJLSvT7J7acXVeRDVrkZwIPa1QN+tKrDtRhtYKZn8vxgHhKhLxil7IsaqIIkCHmcokPRCJrRi95i11ffptH0Fa/Q1y7fhqCn/TlxQ8sTUb2HM8wXcfPujt83LIvnPATnp+t//8kNvGMkPgpWsSvArckti1r6/YEdSQm9tDTFf0rRTFMpQg+LbgMiQgTdcl99xOKon1mkkx0bobioaI0s+LFvVFCKwv2neLDwIDAQAB";
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问



    /**
     * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
     */
    public static String notify_url="http://127.0.0.1:8080/admin/org_alipay/alipay_notify";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，可以正常访问

    /**
     * 此页面是同步返回用户页面，也就是用户支付后看到的页面，上面的notify_url是异步返回商家操作，谢谢
     * 要是看不懂就找度娘，或者多读几遍，或者去看支付宝第三方接口API，不看API直接拿去就用，遇坑不怪别人,要使用外网能访问的ip,建议使用花生壳,内网穿透
     */
    public static String return_url = "http://d954miw.nat.ipyingshe.com/admin/organization/info";

    //前台用户
    public static String notify_url2="http://d954miw.nat.ipyingshe.com/home/user_alipay/alipay_notify";


    public static String return_url2 = "http://d954miw.nat.ipyingshe.com/home/user/index";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl= "https://openapi-sandbox.dl.alipaydev.com/gateway.do"; //"https://openapi.alipaydev.com/gateway.do";

}
