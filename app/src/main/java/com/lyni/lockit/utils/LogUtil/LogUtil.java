package com.lyni.lockit.utils.LogUtil;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;

import java.io.File;

/**
 * @author Liangyong Ni
 * description 基于XLog的自定义Log工具
 * @date 2021/6/13
 */
public class LogUtil {
    private static final long MAX_TIME = 24 * 60 * 60 * 1000;
    private static String logDir = "";

    private static void init() {
        // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
        // 允许打印线程信息，默认禁止
        // 允许打印深度为 2 的调用栈信息，默认禁止
        // 允许打印日志边框，默认禁止
        LogConfiguration config = new LogConfiguration.Builder()
//                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE)
                .enableThreadInfo()
                .enableStackTrace(2)
                .enableBorder()
                .build();

        // 通过 android.util.Log 打印日志的打印器
        Printer androidPrinter = new AndroidPrinter(true);
        // 通过 System.out 打印日志到控制台的打印器
        Printer consolePrinter = new ConsolePrinter();
        // 打印日志到文件的打印器
        // 指定保存日志文件的路径
        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
        // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
        // 指定日志文件清除策略，默认为 NeverCleanStrategy()
        // 指定日志文件清除策略，默认为 NeverCleanStrategy()
        Printer filePrinter = new FilePrinter
                .Builder(logDir)
                .fileNameGenerator(new DateFileNameGenerator())
                .backupStrategy(new NeverBackupStrategy())
                .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))
                .build();

        // 初始化 XLog
        // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
        // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
        XLog.init(config, androidPrinter, consolePrinter, filePrinter);
    }

    public static void setLogDirAndInit(File file) {
        logDir = file.getAbsolutePath();
        init();
    }
}
