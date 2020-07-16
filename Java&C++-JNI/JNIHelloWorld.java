class JNIHelloWorld {
    native void cfucHello();// Declaring the native function

    native int cfuncPassIntegerFromCtoJava(int num);

    native String cfuncPassStringFromCtoJava(String str);

    native int[] cfuncPassIntegerArrayFromCtoJava(int n);

    native String[] cfuncStringArrayFromCtoJava();

    static {
        System.loadLibrary("clib");// Linking the native library which we will be creating.
    }

    public static void main(String[] args) {
        JNIHelloWorld obj = new JNIHelloWorld();
        // C hello world
        obj.cfucHello(); // Calling the native function
        // pass integer
        int num = 3;
        System.out.println("Pass Integer From C to Java: Pass value is " + num + ", Return value is "
                + obj.cfuncPassIntegerFromCtoJava(num));
        // pass string
        String str = "HaouanSu";
        System.out.println("Pass String From C to Java: Pass value is " + str + ", Return value is "
                + obj.cfuncPassStringFromCtoJava(str));
        // pass int[]
        int n = 5;
        System.out.print("Pass Integer Array From C to Java: Pass value is " + n);
        int[] res = obj.cfuncPassIntegerArrayFromCtoJava(n);
        System.out.println(", Return Array are::");
        for (Integer i : res)
            System.out.print(i+"||");

        // pass string array
        String[] days = obj.cfuncStringArrayFromCtoJava();
        System.out.println();
        System.out.println(" > The days of the week are :: ");
        for (String name : days)
            System.out.print(name+"||");
    }
}