package com.neusoft.sdd.util.commonUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.neusoft.sdd.util.algorithm.BASE64;
import com.neusoft.sdd.util.algorithm.DES;
import com.neusoft.sdd.util.algorithm.MD5;

public class EncodeUtil {

    public static String encryptMD5(String str) {
        return encrypt(str, "MD5");
    }

    public static String encryptSHA(String str) {
        return encrypt(str, "SHA");
    }

    public static String encrypt(String str, String algorithm) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.update(str.getBytes());
            return toHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String toHex(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 3);
        for (int i = 0; i < b.length; ++i) {
            sb.append(Character.forDigit((b[i] & 0xF0) >> 4, 16));
            sb.append(Character.forDigit(b[i] & 0xF, 16));
        }
        return ((sb != null) ? sb.toString().toUpperCase() : null);
    }

    public static String encode(String str) {
        return MD5.getInstance().encryption(str);
        // return DES.encode(str);
        // return BASE64.encode(str);
    }

    public static String decode(String str) {
        return MD5.getInstance().encryption(str);
        // return DES.decode(str);
        // return BASE64.decode(str);
    }

    public static String encode(String str, String algorithm) {
        if ("BASE64".equals(algorithm))
            return BASE64.encode(str);
        if ("DES".equals(algorithm))
            return DES.encode(str);
        return new NoSuchAlgorithmException().getMessage();
    }

    public static String decode(String str, String algorithm) {
        if ("BASE64".equals(algorithm))
            return BASE64.decode(str);
        if ("DES".equals(algorithm))
            return DES.decode(str);
        return new NoSuchAlgorithmException().getMessage();
    }

    public static void main(String arg[]) throws Exception {
        
        //System.out.println(DES.decode("piggifibmihmaoefppdobbmbpejflliiblpgoohjcfkcmdjleompbpfilnpakplmdpciknmhggkaehio"));
        //System.out.println(DES.encode("getPersonXml"));
//        System.out.println(new BigDecimal(123456789.02).toString());
//        System.out.println(new BigDecimal("123456789.02").toString());
      //  String str=" Z2RmamdqZOWHoOWPt+WIsOmlreW6l+mZhOi/kSBzZOWNoeWPkemAgeWIsOS6huWPkei0p+mAn+W6pumjnuS4nOaWuemXqueUteWPkeS6huiDnOWPr+eEmuaer+mjn+a3oeWPkei0p+S6humAn+W6puWIhuW8gOWni+aUvuadvueahOeci+azleWlveeci+eahOWPkeS6huaYr+eahOWlveiOseWuouWFi+mHjOaWr+S4geaWueWKm+eUs+Wkp+WPkeWIqeW4guWvueS6huaYr+W/q+S5kOeahOWPkei0p+mAn+W6puWIhuexu+OAguaUtuWIsOS6huWPkei0p+S6humAn+W6puW8gOWPkSDvvIzmmK/nlLXor53otLnmmK/lnLDmlrnmlLvlh7vlipvvvIzlu7rnq4vnmbvorrDpmL/mi4nlhYvlnKPor57oioLjgII=";
      // String fe=EncodeUtil.encode("我是方法地方","BASE64");
      // System.out.println(fe);
      // System.out.println(EncodeUtil.decode(fe, "BASE64"));;
      // System.out.println(EncodeUtil.decode(str, "BASE64"));
//         BASE64 decoder = new BASE64();
         String file="D:\\sjjh.XML";
         StringBuffer sb = new StringBuffer();  
         FileInputStream in = new FileInputStream(file); 
         int b;  
         char ch;  
         while((b=in.read())!=-1){  
             ch = (char)b;  
             sb.append(ch);  
         }  
         in.close();  
         //将buffer转化为string  
         String oldString = new String(sb);  
           
         //使用base64编码  
         System.out.println(oldString+" oldString");
         String ssss=EncodeUtil.encode(oldString, "BASE64");
         System.out.println("-------"+ssss);
         
        // String newString = compressData(oldString); 
         //System.out.println(newString+"  --newString");
       //  String str1="eJztXOtTE8kW/8ytuv/D3HzarRgJSQiWhdkCI48YREVqhC9WVrKA5oFA4uonwRcICHfXVVH0+oAk6ApYundXEK1q/rF7zul59Mz0BLLCrl4zWzVM+nH6vPv0b8Zt/O7HdErJJ4dHBrOZQ566/X6PksyczfYNZvoPebpPtfgOeL6L/PMfjf/y+ZStInvP7m6V2Af2761Ftsk+QssdVlJOd8S7hi4p+YDfH1a6lW8GRkeHDtbWXrx4cX8iNZrNJ/afzaa/VWj0ayWRuaR8A7dvFZ8PSQ9ns6Pwt6Zxa4mtsTGg/5xdh+dpuBfYE/Yent+zWXiehXuJfWRrEfixwH61TCDi0P6a/QdapmBoEcaMAaPrbB3a14DpAoy8ArNWYcwym2fP4HmC/RfuK+zB1iosM81Folm34f4H3JfZS3Zfm1tkGzAX26/Q/QHMvcWeQvsbtqH1FtkDdo3o/Aa9r9gczL3LbsL4DVgLKS9Cywx71lhbgcikoSKbZBNc+EjAX1cPFMQmFy0WQSkb8PyYTUXgz23gcYkV2RRvknIhTtme6na24RP0+xx76aqdAvuFrZF2VqB3lt2C+3Wy0HV21dLLrWWVxlyR9yInL8gqs0D/I8wqwLrvtsCSbHw7uZ3al4k0wR7CfYNc7B4wugz3t2ibgM8f8AWCp/wNB/31B/1h6XJu83ew3BJo4RcKw5lIpW6/LS8icQcvJdD2NbhPk0UEwy+zm2BIHkBovFXonmTL8PwaqKFj34ShK2DCEvDyCu4l4AtNNQEpRIHsw+2mBg/Tw6Q2swhGXpMaa4P9DHQfAztkUskYDNJXRmhNgWPqwbvBNoHjh+wGueQErTUOY36FcMYx65pLysL5KXtO9wnDDU36mEyuarOW2A3iZAWcDh3wKYwZA70gt8jnVXLSeWhx9orSib1iWBSBMiafOZACjYDaBy9wULM7u5sJtSQjj00xNvhWUASTjkVGRvoSlIgqm7bjtdA8M9BSRB9Dz5kkL3ov2mGH64ukcE+Dhofk7iuUya8AUZcFNEOjY8xozl1ky2ydb2Igie7eq2CKIjhRIRJsCIWD4XAI/sBDA2YA5yBt7kP2lphes6eRoM8f8vmDSl3gYDB0MFhPROSjSSBx+0CWUax3MHwJ8kGBEuIETVoif5jVCGDs39BELIG4JWi/D1tfAbS5aNGmGFXcx0Vfw1mrpOWCtnni/ZYWc2ZszdCYaSN6ChR5j9kC8fME7m8oYyyAKTDpY3QuUN5A3opskXIb8iauzqktA5/3yYCLmkl5NFu3FnvG47II6lTcLG4afBIqINz+H1uTMf64RwkYk8EfINp2ydiNFFm0UgbIUM+1IAuCC/n97ktYBm+3nKm/AnsEHlQAseZQ3kVWFCsjWk4+uPwS1vRtpE6lNRpvPhPtOORpjXqUaNNReq6rq/coOE+Bq5GIbpBQP8OsR+weDjwc40M9OI4PxEq20VK7cadCZ7gnqkNMFkqP2nwmjqQuXfw+1Zf2RPhfsuM7Cq47RsoHlwMNVLhCRFFARatUqOjF5QvKZvegfQUy2zLsFpvwPE0xweNSLBWn6PkGm+faJGmVRi178dLbkKKpxxPxB8lORq99kp74MMIeaTsNzyVkXJNYc+uxLk8kHNbIbTNPXEYcgsHOI2JOJH3itCdST+qUDnVjWgxtVFyJgrokat9c5XCsuQP8xCNGS0WkiIuaMiy4RJzIQlQlFvx+wy4VEJIysAxp9YbWgsH2u7lctKn1TLTNEyk/3saHo19bVS78OsShVFQev7a1+ZaIkr6Uru2gJ5XYQUVctasZBKY/Pm5nX52/3lfnA40fqLMt5yAkXa4IwY1HlitW8XpP2YQTxtnWEXpsK9jZB96DfuAXmPXh3W+ybGVQ2O7N6ceisunCUH06ZMd9HbFYvG3f0Zbmtn0Y2vg7hr9P9LTtOxbFn4fbtBxTzudp0zNZiJ882eO0u2NCGd+nfr6sKSyvpNZZ0VypIwbCknhGp3VWEQoorAE2IZRseu6IY1Q4BlB5aWszUo+QgvjhltctmJ+u8arJpN/V0tbTEjvTDCz6iUXpeDGrLbNnUM7QTmGSOYV6LEHPde2+BGX/Ar8DWWGOhUuusnXYLmxin26jzMOdwugX4luxCWotCXG31evpFdoP5yFBOvEU3M4QSbnDPmxZT/1P6OjyCtr1k7u8lxcN2L6kqQ2rjwfadmlWbPoG6kbHjStxrsuBwnB9pyoiHCfbuRLcQSKjqt1nlMd2aa02mDUKYP6Mh/EZGj/jOsupdzeGeARiUY0xcJ02QdwKSxAPv1Kd9wzuEzBLRMioIjoebRErFV19PBrl/iSpu9RYD/f539jv2hltEdJXgfKFdG7EXMB1KYEvcalWqGeG+n6w0RYGR8owj8Uwna1EilEoY0L1ofpgKBhysGzM0I9w4uGV15RYBpZgsxjnRZShRMUmnoUv4bQtFuTa1BqlN3AynUj3XejtVNuynerxUa/aPtKZTg0n1XDKm+4d6BysPd/VfBn6j2WT6vHzyXRTfxLHdIUHcvh70D/kTTdlvGp4KJc+l8l1hTKdCTWXTJ++kEt2j9IcHDsYzQD9YW/ySDqXSA57Yaw3EeQ0kQaO0emo7UD7QH+nmhn0JrrzuURfPpdMDHQmz8Lvy6mkmson8RnbdPqJnmH+G/oGO0eSai6bU1sGvem2c8DPsLcr1J9EvtSjaW+ye8Srnh/y4ly1/UJSDfXnVFhCp6PWXvB21Z/v1OmLMiJvyR9z3s6m/lyiezRn14VFDpTh+PmuaD7ffjmdPlqbScVPZ4frg9nReAZ/pzLxKCg7k07F86mBhnxqcCiPY1O5huHMhVA+k/4+nx0ejg6l47XpwVAmNdCTT2WOHgcaDdnBbPxcf3v7Id2WtEOVMzfdra7He6oZ7M9msD1NW/ZEtUBauEWHqXk6TVoqAjXWop7g1dW2sxx5TTrKRchFgjIldb0ai1F91/cD/idJcpZ5UuqfmI7/1kzcSOOmoO+t2zkz1ksKCgTpPCsdLBDbpIJuHVxRQM5NYr0tRhXpMtagZU0MO3hTw+G9Z3AsWXDLKtT7aIu/lymCGE+0tLDExgmi43AawoElDaReIShjgZAw6/uwaWLo4RZ/G1eAYBnjFJoTI8lwSLEwYb7amaTl38gGcYAb6c1TNhmnlrH2I6dalJMth5WAP1SvJRWe2ZZBnDtwHwfRdP8y313QO4qO9o4j2nsJxG71Nwk8jZgI6gMS6SdYcIxAoU16W2qilByiErEb/d0Fb5+FFIQs34X7TyTpfcqzzncgC6RouXlEw4hobXljcPxTT8IIahHAJdcwWgyRLZ6vZyRGwgMmWvgDeIrOVjlfc9IhA4OK79BcjD3yOw31s141EqH56yZ+ahPwOeIeffU2vTYrpxLdk01n0HOoW1iVwziFc8MOhK3dES33Qm+vA/yceY1Yr5xw6SUK/5U8YFxJ6RUMBEN0bVfSSIoZR0KHk6ne4QCIqYM6jZRTondEVvA44Kn8aGtSEwoU3WqW5T6FtvPdh6Nw0tesYrtVbPfPYLuyVcvAZ7g2h9AqAXddyLpo/osClgP/T8DyBFmJv023EGkhkzsGEJpoayv7zuBzBo8/GVr/OmFnyYajf4Ulvmw3kejWM10xcqb3WhE3Kdl7JCQEK1UIcEtOjlgdVLgxY2Qv2bZnLLJwZJFmbf9loLmd61WnHd2QFYEGlioFcyoQwf0LGvt3Hjozcwbcbhd8aqs83COKw+F8/Su4CTovbQ/07ATmR/ovLPIKhfpOwH5+NhNVvZeoj/NI87nAQE7O9hYXKrfWboD2ZcT4q9F7gZVdRJCcpHcLT6piSlVM6avBlGxJT6KCzxZhEq9dRJtsdN0QmkqgIbmudx0vqnF5AeZo3jLhIZ7sljRoYpIKKSs8FPQI1MWRtpKhCrpUQZcq6PKXgC52e1dBly8WdNkj7KQcmAPeFJYgOMa0KtTySVCLHGwxe7VP9G0l5H16rWGqW1Hc9nrnYEutKv6zAoUjD7wSpWJeS0eW874NUxE6lL3/ck0ohxTDxp8BHrAT5vYWE3DRxy5CA65C/W0IgcHRngEFwgq7iRfwUKliBlXM4KvDDIzQkqjii8AOLNdeAQkGeddPWXbVepV9uOK89gKacAUntoEnaqjQcsIO1mbrJCJofoNifDpj+wqG/lWm/f8VYNRP0NtYq/3fO/4HI6AbNQ==";
        // File newFile = EncodeUtil.string2File(str1, "D:\\1231211.xml");
       //  System.out.println("----------");
        // int[] seeds = new int[3];
        // for(int i=0;i<seeds.length;i++) {
        // System.out.println(i);
        // }
        // String s1 = new String("hello");
        // String s2 = new String("hello");
        // if(s1.equals(s2))
        // System.out.println("equals");
        // else
        // System.out.println("no equals");
        //        
        // StringBuffer sb1 = new StringBuffer("hello");
        // StringBuffer sb2 = new StringBuffer("hello");
        // if(sb1.equals(sb2))
        // System.out.println("equals");
        // else
        // System.out.println("no equals");

//        Queue<String> queue = new PriorityQueue<String>();
//        queue.add("A");
//        queue.add("B");
//        queue.add("C");
//        queue.add("D");
//        for (String q : queue) {
//            System.out.println(q);
//        }
//        queue.remove();
//        queue.add("F");
//        System.out.println("*******");
//        for (String q : queue) {
//            System.out.println(q);
//        }
//
//        System.out.println(EncodeUtil.encode("test"));

//        SimpleObservable observable01 = new SimpleObservable("SimpleObservable01");
//        SimpleObservable observable02 = new SimpleObservable("SimpleObservable02");
//        SimpleObserver observer1_0 = new SimpleObserver("SimpleObserver1-0");
////        SimpleObserver observer1_1 = new SimpleObserver("SimpleObserver1-1");
//        observable01.addObserver(observer1_0);
////        observable01.addObserver(observer1_1);
//        observable02.addObserver(observer1_0);
////        observable02.addObserver(observer1_1);
//        observable01.setData(1);
//        observable01.setData(2);
//        observable01.setData(2);
//        observable01.setData(3);
//        observable02.setData(100);
//        observable02.setData(110);
        // System.out.println(DES.decode("kcknocfgieejpokd"));
    }
    /** 
     * 对文件进行解码 
     * @param oldString 需要解码的字符串 
     * @param filePath  将字符串解码到filepath文件路径 
     * @return  返回解码后得到的文件 
     * @throws Exception 
     */  
    public static File string2File(String oldString,String filePath) throws Exception{  
        File file = new File(filePath);  
        if(file.exists()){  
            System.out.println("文件已经存在，不能将base64编码转换为文件");  
            return null;  
        }else{  
            file.createNewFile();  
        }  
        FileOutputStream out = new FileOutputStream(file);  
          
        //对oldString进行解码  
        String newString = decompressData(oldString);  
          System.out.println(newString+" --newString");
        //将问件内容转为byte[]  
        char[] str = newString.toCharArray();  
        for(char ch:str){  
            byte b = (byte)ch;  
            out.write(b);  
        }  
        out.close();  
        return file;  
    }  
    /** 
     * 使用base64解码字符串 
     * @param encdata 
     * @return 解码后的字符串 
     */  
    public static String decompressData(String encdata) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(getdeBASE64inCodec(encdata));   
            zos.close();  
            return new String(bos.toByteArray());  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "UNZIP_ERR";  
        }  
    } 
    /** 
     * 调用apache的解码方法 
     */  
    public static byte[] getdeBASE64inCodec(String s) {  
        if (s == null)  
            return null;  
        return new Base64().decode(s.getBytes());  
    } 
    public static String compressData(String data) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);  
            zos.write(data.getBytes());  
            zos.close();  
            return getenBASE64inCodec(bos.toByteArray());  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "ZIP_ERR";  
        }  
    } 
    public static String getenBASE64inCodec(byte [] b) {  
        if (b == null)  
            return null;  
        return new String((new Base64()).encode(b));  
    }  
}