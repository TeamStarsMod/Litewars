package xyz.litewars.litewars;

public class ExceptionUtils {
    public static void printException(Exception e) {
        // 打印异常消息
        Litewars.logger.severe("发生异常: " + e.getMessage());

        // 获取堆栈跟踪信息
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            Litewars.logger.severe(element.toString());
        }
    }
    public static void printException(String cause, Exception e) {
        Litewars.logger.severe(cause);
        printException(e);
    }
}
