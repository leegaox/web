package com.example.eureka.servercenter;

import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhangkl
 */
public class CommonUtil {

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 检查对象是否为空 空返回true
     *
     * @param bean
     * @return
     */
    public static boolean checkFieldValueNull(Object bean) {
        boolean result = true;
        if (bean == null) {
            return true;
        }
        Class<?> cls = bean.getClass();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(bean, new Object[]{});
                if (fieldVal != null) {
                    if ("".equals(fieldVal)) {
                        result = true;
                    } else {
                        result = false;
                        return result;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    /**
     * 检查对象是否为空 空返回false
     *
     * @param bean
     * @return
     */
    public static boolean checkFieldValueIsNotNull(Object bean) {
        return !checkFieldValueNull(bean);
    }

    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取随机的length位数字
     *
     * @return
     */
    public static String getRandomChar(Integer length) {
        String str = "";
        for (int j = 0; j < length; j++) {
            String val = "";
            Random random = new Random();
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
            val = val.toLowerCase();
            str += val;
        }
        return str;
    }

    /**
     * 对象转BigDecimal
     *
     * @param obj
     * @return BigDecimal
     * @author wangtao
     */
    public static BigDecimal objectToBigDecimal(Object obj) {
        BigDecimal b = null;
        if (obj != null) {
            if (obj instanceof Double) {
                b = new BigDecimal((Double) obj);
            } else if (obj instanceof BigDecimal) {
                b = (BigDecimal) obj;
            } else if (obj instanceof BigInteger) {
                b = new BigDecimal((BigInteger) obj);
            } else if (obj instanceof String) {
                b = new BigDecimal((String) obj);
            } else if (obj instanceof Long) {
                b = new BigDecimal((Long) obj);
            }
        }

        return b;
    }

    /**
     * 根据身份证计算年龄
     *
     * @param IdNO
     * @return
     */
    public static Integer getAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

    /**
     * 根据身份证获取性别
     *
     * @param IdNO
     * @return
     */
    public static String getSex(String IdNO) {
        int leh = IdNO.length();
        String sex = "1";
        if (leh == 18) {
            if (Integer.parseInt(IdNO.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                sex = "2";// 女
            } else {
                sex = "1";// 男
            }
        } else {
            String usex = IdNO.substring(14, 15);// 用户的性别
            if (Integer.parseInt(usex) % 2 == 0) {
                sex = "2"; // 未知
            } else {
                sex = "1";
            }
        }
        return sex;
    }

    /**
     * 根据身份证获取生日
     *
     * @param IdNO
     * @return
     */
    public static String getBirthDay(String IdNO) {
        int leh = IdNO.length();
        String birthday = "";
        if (leh == 18) {
            birthday = IdNO.substring(6, 14);
        } else {
            birthday = "19" + IdNO.substring(6, 12);
        }
        return birthday;
    }

    /**
     * 对字符串信息进行解码 * @return
     */
    public static String base64Decode(String base64String) {
        try {
            byte[] output = Base64.decodeBase64(base64String);
            return new String(output, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取一个随机数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

}
