package xyz.chaobei.mybatis.resource;

import java.io.InputStream;
import java.util.Objects;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/4
 **/
public class ClassLoaderWrapper {

    /**
     * 默认类加载器
     */
    private ClassLoader defaultClassLoader;
    /**
     * 系统类加载器
     */
    private ClassLoader systemClassLoader;


    public ClassLoaderWrapper() {
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
    }


    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    /**
     * 返回一组类加载器，顺序是：
     * 1.传入的classloader（null）
     * 2.默认类加载器（null）
     * 3.当前线程的类加载器
     * 4.当前类的类加载器
     * 5.系统类加载器
     *
     * @param classLoader
     * @return
     */
    private ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                defaultClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader
        };
    }

    /**
     * <p>
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param resource
     * @return java.io.InputStream
     * @since 2022/3/4
     **/
    public InputStream getResourceAsStream(String resource, ClassLoader[] classLoaders) {

        InputStream inputStream = null;

        for (ClassLoader classLoader : classLoaders) {

            if (null == classLoader) {
                continue;
            }

            // 通过文件名加载
            inputStream = classLoader.getResourceAsStream(resource);

            // 可能加载不到 以/开头
            if (Objects.nonNull(inputStream)) {
                break;
            }
            // 加入一个/试一下
            inputStream = classLoader.getResourceAsStream("/".concat(resource));

            if (Objects.nonNull(inputStream)) {
                break;
            }

        }
        return inputStream;
    }


}
