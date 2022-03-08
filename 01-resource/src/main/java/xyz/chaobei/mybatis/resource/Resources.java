package xyz.chaobei.mybatis.resource;

import java.io.InputStream;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/4
 **/
public class Resources {

    private final static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * <p>从Resource目录中加载一个文件，获得字节流
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param resource
     * @return java.io.InputStream
     * @since 2022/3/4
     **/
    public static InputStream getResourceAsStream(String resource) {
        return getResourceAsStream(resource, null);
    }

    /**
     * <p>
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param resource 文件路径
     * @return java.io.InputStream
     * @since 2022/3/4
     **/
    public static InputStream getResourceAsStream(String resource, ClassLoader classLoader) {

        InputStream inputStream = classLoaderWrapper.getResourceAsStream(resource, classLoader);
        assert inputStream != null;

        return inputStream;
    }


}
