package xyz.litewars.litewars.api;

import org.jetbrains.annotations.Nullable;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.commands.LitewarsCommand;

/**
 * Litewars 插件对外 API，允许外部插件注册和获取主命令实例。
 */
public class LitewarsAPI {

    /**
     * 获得 Litewars 父命令实例。
     * 如果命令尚未初始化完成，返回null。
     * @return 命令实例
     */
    public static @Nullable LitewarsCommand getLitewarsParentCommand() {
        return Litewars.litewarsCommand;
    }
}
