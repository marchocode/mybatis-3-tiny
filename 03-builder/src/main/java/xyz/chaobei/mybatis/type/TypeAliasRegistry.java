package xyz.chaobei.mybatis.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.*;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public class TypeAliasRegistry {

    private final Map<String, Class> TYPE_ALIAS = new HashMap<>();


    public TypeAliasRegistry() {

        registerAlias("string", String.class);

        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);

        registerAlias("byte[]", Byte[].class);
        registerAlias("long[]", Long[].class);
        registerAlias("short[]", Short[].class);
        registerAlias("int[]", Integer[].class);
        registerAlias("integer[]", Integer[].class);
        registerAlias("double[]", Double[].class);
        registerAlias("float[]", Float[].class);
        registerAlias("boolean[]", Boolean[].class);

        registerAlias("_byte", byte.class);
        registerAlias("_long", long.class);
        registerAlias("_short", short.class);
        registerAlias("_int", int.class);
        registerAlias("_integer", int.class);
        registerAlias("_double", double.class);
        registerAlias("_float", float.class);
        registerAlias("_boolean", boolean.class);

        registerAlias("_byte[]", byte[].class);
        registerAlias("_long[]", long[].class);
        registerAlias("_short[]", short[].class);
        registerAlias("_int[]", int[].class);
        registerAlias("_integer[]", int[].class);
        registerAlias("_double[]", double[].class);
        registerAlias("_float[]", float[].class);
        registerAlias("_boolean[]", boolean[].class);

        registerAlias("date", Date.class);
        registerAlias("decimal", BigDecimal.class);
        registerAlias("bigdecimal", BigDecimal.class);
        registerAlias("biginteger", BigInteger.class);
        registerAlias("object", Object.class);

        registerAlias("date[]", Date[].class);
        registerAlias("decimal[]", BigDecimal[].class);
        registerAlias("bigdecimal[]", BigDecimal[].class);
        registerAlias("biginteger[]", BigInteger[].class);
        registerAlias("object[]", Object[].class);

        registerAlias("map", Map.class);
        registerAlias("hashmap", HashMap.class);
        registerAlias("list", List.class);
        registerAlias("arraylist", ArrayList.class);
        registerAlias("collection", Collection.class);
        registerAlias("iterator", Iterator.class);

        registerAlias("ResultSet", ResultSet.class);

    }

    public void registerAlias(String alias, Class classes) {


        if (Objects.isNull(alias)) {
            throw new RuntimeException("Register alias is not be null");
        }

        // 不能存在相同别名和相同的数据
        if (TYPE_ALIAS.containsKey(alias) && TYPE_ALIAS.get(alias) != null && !TYPE_ALIAS.get(alias).equals(classes)) {
            throw new RuntimeException("The alias already bing");
        }

        // 转换为小写
        String key = alias.toLowerCase(Locale.ENGLISH);
        TYPE_ALIAS.put(key, classes);
    }

    /**
     * <p>按照注册的别名，拿出对应的class
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param alias
     * @return java.lang.Class
     * @since 2022/3/8
     **/
    public Class resolveAlias(String alias) {

        if (Objects.isNull(alias)) {
            return null;
        }

        String key = alias.toLowerCase(Locale.ENGLISH);

        if (TYPE_ALIAS.containsKey(key)) {
            return TYPE_ALIAS.get(key);
        } else {
            return null;
        }
    }


}
