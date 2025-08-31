package com.github.existedname.healthcalculatorv3.service;

import com.github.existedname.healthcalculatorv3.model.entity.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件服务类, 提供对文件的操作方法, 目前用于将用户数据保存到项目文件中
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/30 8:04
 */
public final class FileService {
    // ==================== 常量 ====================
    /** 用户数据储存文件的名称 */
    private static final String USER_FILE_NAME = "user.txt";
    /**
     * 用户数据储存文件的相对路径
     * <pre>
     *     Windows: src\com\github\existedname\healthcalculatorv3\USER_FILE_NAME
     *     Mac/Linux: src/com/github/existedname/healthcalculatorv3/USER_FILE_NAME
     * </pre>
     */
//    private static final String USER_FILE_PATH = Paths.get( "src", "com", "github", "existedname", "healthcalculatorv3", USER_FILE_NAME ).toString();
    private static final String USER_FILE_PATH = Paths.get( "healthcalculatorv3", USER_FILE_NAME ).toString();

    // ==================== 静态变量 ====================
    private static FileService fileService = null;

    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private FileService(){ }

    // ==================== 公有方法 ====================
    public static FileService getInstance(){
        if ( Objects.isNull( fileService ) ){
            fileService = new FileService();
        }
        return fileService;
    }

    /**
     * 将用户数据存入项目中的文本文件, 并打印操作信息及结果<br>
     * <B>默认储存路径: {@link #USER_FILE_PATH }</B>
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     * @throws IOException
     */
    public void saveUserToFile( User user ) throws IOException{
        Objects.requireNonNull( user, "参数 user 不能为 null" );
        String operation = "正在保存用户数据", result = "";
        UIService.getInstance().printLoadingProgress( operation, result );
        saveTextToFile( USER_FILE_PATH, user.toString() );
        System.out.println( "\t已将用户数据保存到 " + USER_FILE_NAME );
        System.out.println();
    }




    // ==================== 私有辅助方法 ====================
    /**
     * 将文本存入指定路径的文件, 细节由重载方法 {@link #saveTextToFile( File, String text ) } 实现
     *
     * @param filePath 指定路径
     * @param text 待储存的文本
     *
     * @throws NullPointerException 当 filePath 或 text 为 null 时
     * @throws IllegalArgumentException 当 user 为 null 时
     * @throws IOException
     */
    private void saveTextToFile( String filePath, String text ) throws IOException {
        Objects.requireNonNull( filePath, "参数 filePath( 指定的文件路径 )不能为 null" );
        Objects.requireNonNull( text, "参数 text( 待储存的文本 )不能为 null" );
        saveTextToFile( new File( filePath ), text );
    }

    /**
     * 将文本存入指定的文件
     *
     * @param file 指定文件
     * @param text 待储存的文本
     *
     * @throws NullPointerException 当 file 或 text 为 null 时
     * @throws IllegalArgumentException 当 user 为 null 时
     * @throws IOException
     */
    private void saveTextToFile( File file, String text ) throws IOException  {
        Objects.requireNonNull( file, "参数 file( 指定的文件 )不能为 null" );
        Objects.requireNonNull( text, "参数 text( 待储存的文本 )不能为 null" );
        if ( ! file.exists() ){
            if ( file.createNewFile() ){
                System.out.println( "\t成功创建路径为 " + file.getPath() + " 的文件 !" );
            } else {
                throw new IOException( "路径为 " + file.getPath() + " 的文件不存在并且创建文件失败" );
            }
        }

        try (
            // 字符输出缓冲流 -套- 转换流 -套- 字节输出流
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream( file, false ), // 取消追加, 直接覆盖原文件
                            StandardCharsets.UTF_8 // UTF-8 编码
                    ), 8192 // 8KB 缓冲
            )
        ){
            writer.write( text );
        } catch ( FileNotFoundException e ){
            throw new FileNotFoundException( "未找到路径为 " + file.getPath() + " 的文件" );
        } catch ( IOException e ){
            throw e;
        }
    }

}
