package xyz.litewars.litewars.utils;

public class ArgumentUtils {
    public static void checkArgument (Object object, CheckMethod method) {
        if (method.check(object)) throw new IllegalArgumentException("参数异常，" + method.errorMsg);
    }

    public enum CheckMethod {
        NOT_NULL ("此形参不能为空！") {
            @Override
            protected boolean check (Object o) {
                return o == null;
            }
        },

        NOT_EMPTY ("此形参不能为空字符串（或必须为字符串）！") {
            @Override
            protected boolean check (Object o) {
                return o instanceof String && ((String) o).isEmpty();
            }
        }; // 高亮活了！

        protected final String errorMsg;

        protected abstract boolean check (Object o);

        CheckMethod (String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
