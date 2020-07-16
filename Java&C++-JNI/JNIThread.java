
public class JNIThread {

    public native static void jniThreads();
    public static void main(String[] args) {
        jniThreads();
    }
}