#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zao_zaochat_admin_ZaoActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
