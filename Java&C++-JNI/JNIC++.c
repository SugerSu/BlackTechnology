#include <jni.h>
#include <stdio.h>

 


JNIEXPORT void JNICALL Java_JNIHelloWorld_cfuction(JNIEnv *env, jobject jobj)
{
   printf("\n > C says HelloWorld !\n");
}
/*
 * Class:     JNIHelloWorld
 * Method:    cfucHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_JNIHelloWorld_cfucHello(JNIEnv *env, jobject jobj)
{
   printf("\n > C says HelloWorld !\n");
}

/*
 * Class:     JNIHelloWorld
 * Method:    cfuncPassIntegerFromCtoJava
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_JNIHelloWorld_cfuncPassIntegerFromCtoJava(JNIEnv *env, jobject jobj, jint num)
{
   jint result = 1;
   while (num > 0)
   {
      result *= num;
      num--;
   }
   return result;
}

/*
 * Class:     JNIHelloWorld
 * Method:    cfuncPassStringFromCtoJava
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_JNIHelloWorld_cfuncPassStringFromCtoJava(JNIEnv *env, jobject jobj, jstring str)
{
   const char *org;
   char *rev;

   org = (*env)->GetStringUTFChars(env, str, NULL);

   int i;
   int size = (*env)->GetStringUTFLength(env, str);

   for (i = 0; i < size; i++)
      rev[i] = org[size - i - 1];

   rev[size] = '\0';

   return (*env)->NewStringUTF(env, rev);
}

/*
 * Class:     JNIHelloWorld
 * Method:    cfuncPassIntegerArrayFromCtoJava
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_JNIHelloWorld_cfuncPassIntegerArrayFromCtoJava(JNIEnv *env, jobject jobj, jint n)
{
   jintArray fiboarray = (*env)->NewIntArray(env, n);

   int first = 0;
   int second = 1;
   int next;
   int i;
   int fibo[n];

   for (i = 0; i < n; i++)
   {
      if (i <= 1)
         next = i;
      else
      {
         next = first + second;
         first = second;
         second = next;
      }

      fibo[i] = next;
   }

   (*env)->SetIntArrayRegion(env, fiboarray, 0, n, fibo);

   return fiboarray;
}

/*
 * Class:     JNIHelloWorld
 * Method:    cfuncStringArrayFromCtoJava
 * Signature: ()[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_JNIHelloWorld_cfuncStringArrayFromCtoJava(JNIEnv *env, jobject jobj)
{
   char *days[] = {"Sunday",
                   "Monday",
                   "Tuesday",
                   "Wednesday",
                   "Thursday",
                   "Friday",
                   "Saturday"};

   jstring str;
   jobjectArray day = 0;
   jsize len = 7;
   int i;

   day = (*env)->NewObjectArray(env, len, (*env)->FindClass(env, "java/lang/String"), 0);

   for (i = 0; i < 7; i++)
   {
      str = (*env)->NewStringUTF(env, days[i]);
      (*env)->SetObjectArrayElement(env, day, i, str);
   }

   return day;
}
