/*
 */
package com.huizhixin.smart.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 */
public final class StringUtils
{
    /**
     * 私有化构造器
     */
    private StringUtils()
    {
    }
    
    /**
     * 处理null的情况
     * 
     * @param s string
     * @return 处理后的string
     * @see [类、类#方法、类#成员]
     */
    public static String dealNull(String s)
    {
        return null == s ? "" : s;
    }
    
    /**
     * 是否为空,null/""都被当做空，只要有一个为空就返回true
     * 
     * @param sArr string
     * @return isNull?
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String... sArr)
    {
        if (sArr == null)
        {
            return true;
        }
        
        for (String str : sArr)
        {
            if (null == str || str.trim().length() == 0)
            {
                return true;
            }
        }
        return false;
        
    }
    
    /**
     * list校验为空
     *
     * @param list 要被检测的list
     * @return 空值时返回true,否则返回false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(List<?> list)
    {
        if ((null == list) || (list.isEmpty()))
        {
            return true;
        }
        return false;
    }
    
    /**
     * trim掉源字符串两头的不可见字符
     * <p>
     * 如果没有匹配到，返回源字符串
     * <p>
     * 
     * @param source 源字符串
     * @return 源字符串左右两侧的不可见字符被去掉
     * @see [类、类#方法、类#成员]
     */
    public static String trimNonVisibleCharator(String source)
    {
        if (null == source)
        {
            return "";
        }
        
        Pattern p = Pattern.compile("\\s*(\\S*)\\s*");
        Matcher m = p.matcher(source);
        if (m.matches())
        {
            return m.group(1);
        }
        else
        {
            return source;
        }
    }
    
    /**
     * 在一层上进行排序 我们对a{b{},c{d{},e{}}} 这样的按照parent来分组。
     * <p>
     * 上面的字符串等价于如下的树
     * <p>
     * a b c d e
     * <p>
     * 该函数会将其解析成如下数组
     * <p>
     * [a], [b, c], [d, e]
     * <p>
     * 
     * @param source String
     * @return List<List<String>>
     * @see [类、类#方法、类#成员]
     */
    public static List<List<String>> parseToSibling(String source)
    {
        Stack<String> stack = new Stack<String>();
        // 遍历时的层元素栈，存放各层元素list，而每层的元素list又都包含n个元素。
        List<List<String>> elements = new LinkedList<List<String>>();
        
        // 待分析字符串的字符数组
        char sourceChareArray[] = source.toCharArray();
        
        // 遍历字符串时的标记点，起始和结束位置。
        int begin = 0;
        int end = 0;
        
        // 遍历字符串，即是将字符串转化为栈内元素的过程
        for (; end < sourceChareArray.length; end++)
        {
            if (sourceChareArray[end] == '{')
            {
                String a = new String(sourceChareArray, begin, end - begin);
                if (a.length() != 0)
                {
                    stack.add(a);
                }
                stack.add("{");
                
                // begin先预置到“{”后一个元素
                begin = end + 1;
            }
            else if (sourceChareArray[end] == '}')
            {
                // 存放一组元素
                List<String> flatElementList = new LinkedList<String>();
                
                // pop form stack until got a element "{"
                String element = stack.pop();
                while (!"{".equals(element))
                {
                    flatElementList.add(element);
                    if (!stack.isEmpty())
                    {
                        element = stack.pop();
                    }
                }
                
                // if is not empty, add this list
                if (!flatElementList.isEmpty())
                {
                    // 由于flatElementList是从栈里pop出来的，因此这里需要对这个list进行倒叙
                    Collections.reverse(flatElementList);
                    
                    elements.add(flatElementList);
                }
                
                // begin先预置到“}”后一个元素
                begin = end + 1;
            }
            else if (sourceChareArray[end] == ',')
            {
                // begin先预置到“,”后一个元素
                begin = end + 1;
            }
            else
            {
            }
        }
        
        return elements;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param source 要被解析的字符串，形如 a=1101&a=1201&b=2321&b=1237
     * @return key为areaId，value为按顺序排列的id，如上面的字符串会被解析为如下形式Map
     *         <p>
     *         {b=[2321, 1237], a=[1101, 1201]}
     *         <p>
     */
    public static Map<String, List<String>> parseByAreaIds(String source)
    {
        Map<String, List<String>> results = new HashMap<String, List<String>>();
        String strs[] = source.split("\\|");
        for (String str : strs)
        {
            // 获得一个形如{"a[]", 1207}的数组，第一个元素为key，第二个元素为value
            String[] keyAndValue = str.split("=");
            
            // 把key的中括号截断掉
            
            List<String> values = results.get(keyAndValue[0]);
            
            if (values == null)
            {
                values = new LinkedList<String>();
                values.add(keyAndValue[1]);
                
                results.put(keyAndValue[0], values);
            }
            else
            {
                values.add(keyAndValue[1]);
            }
        }
        return results;
    }
    
    /**
     * 将字符串中的sql查询关键字[%_\']进行转义 ['aa%''\%_] 转义后为 [''aa\%''''\\\%\_]
     * 
     * @param value 输入字符串
     * @return 转义后的字符串
     */
    public static String sqlEscape(String value)
    {
        if (value == null || value.length() == 0)
        {
            return value;
        }
        StringBuffer buffer = new StringBuffer();
        for (char c : value.toCharArray())
        {
            if (c == '\\' || c == '%' || c == '_' || c == '\uFF05')
            {
                buffer.append('\\');
            }
            else if (c == '\'')
            {
                buffer.append('\'');
            }
            buffer.append(c);
        }
        return buffer.toString();
    }
    
    /**
     * <一句话功能简述> 加密方法 <功能详细描述> 从ESG移植
     * 
     * @param strSrc 要加密的字符串
     * @param type 加密类型
     * @return [参数说明] String
     * @see [类、类#方法、类#成员]
     */
    public static String encrypt(String strSrc, String type)
    {
        MessageDigest messDt = null;
        String strDes = null;
        
        byte[] strString = strSrc.getBytes();
        try
        {
            if (null == type || "".equals(type))
            {
                type = "SHA-256";
            }
            messDt = MessageDigest.getInstance(type);
            messDt.update(strString);
            strDes = bytes2Hex(messDt.digest()); // to HexString
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        return strDes;
    }
    
    /**
     * bytes2Hex <功能详细描述>
     * 
     * @param bts byte[]
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String bytes2Hex(byte[] bts)
    {
        StringBuffer des = new StringBuffer();
        String tmp = null;
        for (int i = 0; i < bts.length; i++)
        {
            tmp = Integer.toHexString(bts[i] & 0xFF);
            if (tmp.length() == 1)
            {
                des.append(0);
            }
            des.append(tmp);
        }
        return des.toString();
    }
    
    /**
     * <一句话功能简述>将jsp标签中的特殊字符进行转义 <功能详细描述>
     * 
     * @param message message
     * @return message
     */
    public static String filter(String message)
    {
        if (null == message || "".equals(message))
        {
            return null;
        }
        
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        
        for (int i = 0; i < content.length; i++)
        {
            switch (content[i])
            {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '\u201C':
                    result.append("&quot;");
                    break;
                default:
                    result.append(content[i]);
                    break;
            }
        }
        
        return result.toString();
    }
    
    /**
     * 对WML中显示的文字进行转义
     * 
     * @param text 原始文本
     * @return WML方式转义后的文本
     */
    public static String escapeWml(String text)
    {
        if (text == null)
        {
            return null;
        }
        // 避免二次转换
        text = text.replace("&amp;", "&");
        char content[] = new char[text.length()];
        text.getChars(0, text.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        
        for (int i = 0; i < content.length; i++)
        {
            switch (content[i])
            {
                case '>':
                    result.append("&gt;");
                    break;
                case '<':
                    result.append("&lt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                case '$':
                    result.append("$$");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        
        return result.toString();
    }
    
    /**
     * 将XML报文解析成map形式的对象。 键是报文的节点，从第二层开始算起，如果是深层节点就使用点分隔符； 值是相应节点名的值的集合。
     * 例如有xml：<body><aa><bb>1</bb><bb>2</bb></aa></body> 将被解析成一个这样的map：键为aa.bb，值为一个链表，它含有两个值：1和2。
     * 
     * @param root 根节点
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, List<String>> parseXmlToMap(org.jdom.Element root)
    {
        Map<String, List<String>> ret = new LinkedHashMap<String, List<String>>();
        
        // 取得全部下一层的孩子们
        List<org.jdom.Element> children = root.getChildren();
        
        // 遍历下一层的孩子们
        for (org.jdom.Element element : children)
        {
            StringUtils.iterateSubNodes(element, "", ret);
        }
        
        return ret;
    }
    
    /**
     * 递归方法，遍历其下一层的节点，如果是到叶子了，就将路径信息加入map
     * 
     * @param root 根节点
     * @param path 路径信息
     * @param ret map
     */
    @SuppressWarnings("unchecked")
    private static void iterateSubNodes(org.jdom.Element root, String path, Map<String, List<String>> ret)
    {
        String paths = path;
        // 取得全部下一层的孩子们
        List<org.jdom.Element> children = root.getChildren();
        
        // 如果路径本来不为空的话，需要加上点分隔符
        if (!StringUtils.isEmpty(paths))
        {
            paths += ".";
        }
        
        // 加上本层节点名
        paths += root.getName();
        
        // 如果没有孩子了，那就是叶子，就需要把信息记入map
        if (null == children || children.isEmpty())
        {
            StringUtils.addNodeInfoToMap(paths, root.getValue(), ret);
            return;
        }
        
        // 遍历下一层的孩子们
        for (org.jdom.Element element : children)
        {
            StringUtils.iterateSubNodes(element, paths, ret);
        }
    }
    
    /**
     * 将叶子的节点信息写入map
     * 
     * @param path 路径
     * @param info 节点内容
     * @param ret map
     * @see [类、类#方法、类#成员]
     */
    private static void addNodeInfoToMap(String path, String info, Map<String, List<String>> ret)
    {
        String infomation = info;
        List<String> infoList = ret.get(path);
        
        infomation = StringEscapeUtils.unescapeXml(infomation);
        
        // 若是新路径
        if (null == infoList)
        {
            infoList = new LinkedList<String>();
            infoList.add(infomation);
            ret.put(path, infoList);
        }
        // 若是老路径
        else
        {
            infoList.add(infomation);
        }
    }
    
    /**
     * 格式化固定长度的格式,不足补0
     * 
     * @param srcStr
     * @param length 要求的长度
     * @return
     */
    public static String formatCode(String srcStr, int length)
    {
        int l = srcStr.length();
        
        if (l >= length)
        {
            return srcStr;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - l; i++)
        {
            sb.append("0");
        }
        sb.append(srcStr);
        return sb.toString();
    }
}
